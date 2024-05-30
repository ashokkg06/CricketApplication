package com.example.cricketapp.Controller;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.cricketapp.DBService;
import com.example.cricketapp.DTO.ReportDTO;
import com.example.cricketapp.Model.MatchReport;
import com.example.cricketapp.Service.FileStorageService;
import com.example.cricketapp.Service.MatchReportService;
import com.example.cricketapp.config.LoggerConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("")
public class HomeController {

    @Autowired
    DBService dbService;

    @Autowired
    MatchReportService matchReportService;

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

    @GetMapping("/reports/{request}")
    public ResponseEntity<ReportDTO> getReports(@PathVariable("request") String request) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("scorecard", "http://localhost:8080/iplseries/2023/matches/1");
        map.put("info", "http://localhost:8080/iplseries/2023/matches/1/info");
        map.put("commentary", "http://localhost:8080/iplseries/2023/matches/1/commentary");
        map.put("points-table", "http://localhost:8080/iplseries/2023/matches/1/points-table");
        map.put("squads", "http://localhost:8080/iplseries/2023/matches/1/squads");
        ReportDTO report = new ReportDTO();
        report.setMatchReport(matchReportService.getMatchReports(map.get(request)));
        report.setPerfMonReport(matchReportService.getPerfMonReports(map.get(request)));
        logger.info(report + " Reports generated");
        return ResponseEntity.ok(report);
    }
}
