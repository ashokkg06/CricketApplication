package com.example.cricketapp.Controller;

import com.example.cricketapp.DBService;
import com.example.cricketapp.DTO.Adminresponse;
import com.example.cricketapp.DTO.CreateAdminRequest;
import com.example.cricketapp.DTO.CreateLoginRequest;
import com.example.cricketapp.Model.Admin;
import com.example.cricketapp.Service.*;
import com.example.cricketapp.config.LoggerConfig;
import com.example.cricketapp.security.JWTAuthentication;
import com.example.cricketapp.security.JWTService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    private final ModelMapper modelMapper;

    private final JWTService jwtService;

    private final FileStorageService fileStorageService;

    private final DBService dbService;

    private final Logger logger = LoggerConfig.getLogger();
    public AdminController(AdminService adminService, ModelMapper modelMapper, JWTService jwtService, FileStorageService fileStorageService, DBService dbService) {
        this.adminService = adminService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.fileStorageService = fileStorageService;
        this.dbService = dbService;
    }

    @PostMapping("/register")
    public ResponseEntity<Adminresponse> register(@RequestBody CreateAdminRequest req) {
        try {
            Admin admin = adminService.createAdmin(req);
            var adminResponse = modelMapper.map(admin, Adminresponse.class);
            System.out.println(adminResponse.getId());
            logger.info(String.valueOf(adminResponse.getId()));
            adminResponse.setToken(jwtService.createJwt(adminResponse.getId()));
            System.out.println("jwt token created");
            logger.info("CREATED: JWT TOKEN");
            return ResponseEntity.ok(adminResponse);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<Adminresponse> login(@RequestBody CreateLoginRequest req) throws Exception {
        try {
            Admin admin = adminService.loginAdmin(req);
            var adminResponse = modelMapper.map(admin, Adminresponse.class);
            adminResponse.setToken(jwtService.createJwt(adminResponse.getId()));
            System.out.println("jwt token created");
            logger.info("CREATED: JWT TOKEN");
            return ResponseEntity.ok(adminResponse);
        } catch (AdminService.AdminNotFoundException | AdminService.InvalidCredentialsException e) {
            if(e instanceof AdminService.AdminNotFoundException) {
                System.out.println("Admin Not found");
                logger.info("Admin Not Found");
                Adminresponse response = new Adminresponse();
                response.setMessage("Admin Not Found");
                return ResponseEntity.ok(response);
            }
            else {
                System.out.println("Invalid email or password");
                logger.info("Invalid email or password");
                Adminresponse response = new Adminresponse();
                response.setMessage("Invalid email or password");
                return ResponseEntity.ok(response);
            }
        }
    }

//    @GetMapping("/admin-panel")
//    public String getToken(Authentication authentication) {
//        var jwtAuthentication = (JWTAuthentication) authentication;
//        var jwt = jwtAuthentication.getCredentials();
//        var adminId = jwtService.retrieveUserId(jwt);
//        System.out.println("Admin Id: " + adminId);
//        return jwt;
//    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            List<String> response = new ArrayList<>();
            boolean isUploaded = fileStorageService.storeFile(file);
            if (isUploaded) {
                response.add("File uploaded successfully: " + file.getOriginalFilename());
            } else {
                response.add("File Already Exists");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    @PostMapping("/addData")
    public ResponseEntity<List<String>> addData(@RequestParam("file1") String file1, @RequestParam("file2") String file2) {
        try {
            String currentWorkingDir = System.getProperty("user.dir");
            String filePath1 = Paths.get(currentWorkingDir, "src/main/resources/" + file1).toString();
            String filePath2 = Paths.get(currentWorkingDir, "src/main/resources/" + file2).toString();
            FileReader reader = new FileReader(filePath2);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            String date = "";
            for(CSVRecord csv: csvParser) {
                date = csv.get("date");
                break;
            }
            System.out.println(date);
            System.out.println(filePath1);
            System.out.println(filePath2);
            String[] str = date.split("-");
            int season = Integer.parseInt(str[2]);
            dbService.addDataSet(season, filePath1, filePath2);
            return ResponseEntity.ok(List.of(String.valueOf(season)));
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }
}