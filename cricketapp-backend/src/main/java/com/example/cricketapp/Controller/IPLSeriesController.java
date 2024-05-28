package com.example.cricketapp.Controller;

import com.example.cricketapp.DTO.PlayerStatsDTO;
import com.example.cricketapp.DTO.PointsTableDTO;
import com.example.cricketapp.Model.*;
import com.example.cricketapp.Service.*;
import com.example.cricketapp.config.LoggerConfig;
import com.example.cricketapp.keyspace.Keyspace;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/iplseries/{season}")
public class IPLSeriesController {
    private final MatchStatisticsService matchStatisticsService;
    private final PointsTableService pointsTableService;
    private final PlayerStatsService playerStatsService;
    private final TeamStatsService teamStatsService;

    private final Logger logger = LoggerConfig.getLogger();
    public IPLSeriesController(MatchStatisticsService matchStatisticsService, PointsTableService pointsTableService, PlayerStatsService playerStatsService, TeamStatsService teamStatsService) {
        this.matchStatisticsService = matchStatisticsService;
        this.pointsTableService = pointsTableService;
        this.playerStatsService = playerStatsService;
        this.teamStatsService = teamStatsService;
    }

    @GetMapping("")
    public String home() {
        return "series";
    }

//    @GetMapping("/matches")
//    public ModelAndView showMatchStatistics(ModelAndView mv) throws SQLException {
//        List<MatchStatistics> statistics = matchStatisticsService.getMatchStatistics();
////        for(MatchStatistics ms : statistics) System.out.println(ms.toString());
//        mv.addObject("statistics", statistics);
//        mv.setViewName("matches");
//        return mv;
//    }
    @GetMapping("/matches")
    public ResponseEntity<List<MatchStatistics>> showMatchStatistics(@PathVariable("season") int season) throws SQLException {
        try {
            Keyspace.setKeyspaceName(season);
            List<MatchStatistics> statistics = matchStatisticsService.getMatchStatistics(String.valueOf(season));
            //        for(MatchStatistics ms : statistics) System.out.println(ms.toString());
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }
//    @GetMapping("/points-table")
//    public ModelAndView getPointsTable() {
//        ModelAndView mv = new ModelAndView();
//        MatchStatistics matchStatistics = new MatchStatistics();
//        List<PointsTableData> list = pointsTableService.getPointsTable();
//        mv.addObject("statistics", matchStatistics);
//        mv.addObject("data", list);
//        mv.setViewName("pointstable");
//        return mv;
//    }

    @GetMapping("/points-table")
    public ResponseEntity<PointsTableDTO> getPointsTable(@PathVariable("season") int season) {
        try {
            Keyspace.setKeyspaceName(season);
            List<PointsTableData> list = pointsTableService.getPointsTable(season);

            PointsTableDTO pointsTableDTO = new PointsTableDTO();
            pointsTableDTO.setPointsTableData(list);

            return ResponseEntity.ok(pointsTableDTO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

//    @GetMapping("/playerstats/{playerName}")
//    public ModelAndView playerStats(@PathVariable("playerName") String playerName) {
//        playerName = playerName.replace("-", " ");
//        ModelAndView mv = new ModelAndView();
//        if(playerStatsService.isBatter(playerName)) {
//            PlayerBattingStats batter = playerStatsService.getPlayerBattingStats(playerName);
//            System.out.println(batter.toString());
//            mv.addObject("batter", batter);
//        }
//        if(playerStatsService.isBowler(playerName)) {
//            PlayerBowlingStats bowler = playerStatsService.getPlayerBowlingStats(playerName);
//            System.out.println(bowler.toString());
//            mv.addObject("bowler", bowler);
//        }
//        mv.addObject("name", playerName);
//        mv.setViewName("playerstats");
//        return mv;
//    }

    @GetMapping("/playerstats/{playerName}")
    public ResponseEntity<PlayerStatsDTO> playerStats(@PathVariable("season") int season ,@PathVariable("playerName") String playerName) {
        try {
            Keyspace.setKeyspaceName(season);
            playerName = playerName.replace("-", " ");
            PlayerStatsDTO playerStatsDTO = new PlayerStatsDTO();

            if (playerStatsService.isBatter(playerName)) {
                PlayerBattingStats batting = playerStatsService.getPlayerBattingStats(playerName, season);
                playerStatsDTO.setPlayerBattingStats(batting);
            }
            if (playerStatsService.isBowler(playerName)) {
                PlayerBowlingStats bowling = playerStatsService.getPlayerBowlingStats(playerName, season);
                playerStatsDTO.setPlayerBowlingStats(bowling);
            }
            return ResponseEntity.ok(playerStatsDTO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

//    @GetMapping("/teams")
//    public ModelAndView getTeams() {
//        ModelAndView mv = new ModelAndView();
//        List<PointsTableData> list = pointsTableService.getPointsTable();
//        mv.addObject("teams", list);
//        mv.setViewName("teams");
//        return mv;
//    }

    @GetMapping("/teams")
    public ResponseEntity<List<PointsTableData>> getTeams(@PathVariable("season") int season) {
        try {
            Keyspace.setKeyspaceName(season);
            List<PointsTableData> list = pointsTableService.getPointsTable(season);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }
//    @GetMapping("/teams/{teamName}")
//    public ModelAndView getTeamStats(@PathVariable("teamName") String teamName) {
//        teamName = teamName.replace("-", " ");
//        ModelAndView mv = new ModelAndView();
//        TeamStats team = teamStatsService.getTeamStats(teamName);
//        mv.addObject("team", team);
//        mv.setViewName("teamstats");
//        return mv;
//    }

    @GetMapping("/teams/{teamName}")
    public ResponseEntity<TeamStats> getTeamStats(@PathVariable("season") int season, @PathVariable("teamName") String teamName) {
        try {
            Keyspace.setKeyspaceName(season);
            teamName = teamName.replace("-", " ");

            TeamStats team = teamStatsService.getTeamStats(teamName, season);

            return ResponseEntity.ok(team);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

}