package com.example.cricketapp.Controller;

import com.example.cricketapp.DBService;
import com.example.cricketapp.Service.FileStorageService;
import com.example.cricketapp.config.LoggerConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("")
public class HomeController {

    @Autowired
    DBService dbService;

    private final Logger logger = LoggerConfig.getLogger();

    @RequestMapping("")
    public ModelAndView homePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        return mv;
    }
    @RequestMapping("iplseries")
    public ModelAndView iplPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        return mv;
    }

    @GetMapping("/getSeasons")
    public ResponseEntity<List<String>> getSeasons() throws IOException, URISyntaxException {
        try {
            System.out.println("Getting list of seasons");
            logger.info("Getting list of seasons:");
            return ResponseEntity.ok(dbService.getCsvFilenames());
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

}
