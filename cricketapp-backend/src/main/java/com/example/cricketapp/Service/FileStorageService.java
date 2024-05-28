package com.example.cricketapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    public FileStorageService() {
        String currentWorkingDir = System.getProperty("user.dir");
        String relativePath1 = "src/main/resources";
        this.fileStorageLocation = Paths.get(currentWorkingDir, relativePath1)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public boolean storeFile(MultipartFile file) {
        try {
            // Normalize file name
            String fileName = file.getOriginalFilename();

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<String> getCsvFilenames() throws IOException, URISyntaxException {
        HashSet<String> set = new HashSet<>();
        Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("classpath:/*.csv");
        for (Resource resource : resources) {
            String name = resource.getFilename().replace(".csv", "");
            String season = name.substring(name.length()-4);
            System.out.println(name);
            System.out.println(season);
            if(isDigits(season))
                set.add(season);
        }
        List<String> filenames = new ArrayList<>(set);
        System.out.println("Files:");
        filenames.forEach(System.out::println);
        return filenames;
    }

    public boolean isDigits(String season) {
        for(int i=0; i<season.length(); i++)
            if(!Character.isDigit(season.charAt(i))) return false;
        return true;
    }
}