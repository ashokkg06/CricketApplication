package com.example.cricketapp.Controller;

import com.example.cricketapp.DTO.*;
import com.example.cricketapp.Model.*;
import com.example.cricketapp.Service.*;
import com.example.cricketapp.config.LoggerConfig;
import com.example.cricketapp.keyspace.Keyspace;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/iplseries/{season}/matches")
public class MatchesController {

    private final PointsTableService pointsTableService;

    private final MatchStatisticsService matchStatisticsService;

    private final PlayerSquadService playerSquadService;

    private final MatchDetailsService matchDetailsService;

    private final MatchCommentService matchCommentService;

    private final BatterMatchService batterMatchService;

    private final BowlerMatchService bowlerMatchService;

    private final ExtrasDataService extrasDataService;

    private final Logger logger = LoggerConfig.getLogger();
    public MatchesController(PointsTableService pointsTableService, MatchStatisticsService matchStatisticsService, PlayerSquadService playerSquadService, MatchDetailsService matchDetailsService, MatchCommentService matchCommentService, BatterMatchService batterMatchService, BowlerMatchService bowlerMatchService, ExtrasDataService extrasDataService) {
        this.pointsTableService = pointsTableService;
        this.matchStatisticsService = matchStatisticsService;
        this.playerSquadService = playerSquadService;
        this.matchDetailsService = matchDetailsService;
        this.matchCommentService = matchCommentService;
        this.batterMatchService = batterMatchService;
        this.bowlerMatchService = bowlerMatchService;
        this.extrasDataService = extrasDataService;
    }

    @GetMapping("{matchNo}")
    public ResponseEntity<ScorecardDTO> getScorecard(@PathVariable("season") int season, @PathVariable("matchNo") int matchNo) throws SQLException {
        try {
            Keyspace.setKeyspaceName(season);
            MatchStatistics matchStatistics = matchStatisticsService.getMatchStatistics(matchNo, season);
            List<List<BatterMatchStats>> batting = batterMatchService.getBatterMatchStats(matchNo, season);
            List<List<BowlerMatchStats>> bowling = bowlerMatchService.getBowlerMatchStats(matchNo, season);
            List<ExtrasData> extras = extrasDataService.getExtrasData(matchNo, season);

            List<TotalData> dataList = new ArrayList<>();

            TotalData td1 = new TotalData();
            td1.setOvers(matchStatistics.getTeam1Overs());
            td1.setScore(matchStatistics.getTeam1Score());
            td1.setWickets(matchStatistics.getTeam1Wickets());
            dataList.add(td1);

            TotalData td2 = new TotalData();
            td2.setOvers(matchStatistics.getTeam2Overs());
            td2.setScore(matchStatistics.getTeam2Score());
            td2.setWickets(matchStatistics.getTeam2Wickets());
            dataList.add(td2);

            ScorecardDTO scorecardDTO = new ScorecardDTO();
            scorecardDTO.setMatchStatistics(matchStatistics);
            scorecardDTO.setBatting(batting);
            scorecardDTO.setBowling(bowling);
            scorecardDTO.setExtras(extras);
            scorecardDTO.setTotal(dataList);

            return ResponseEntity.ok(scorecardDTO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    @GetMapping("/{matchNo}/info")
    public ResponseEntity<MatchInfoDTO> getMatchDetails(@PathVariable("season") int season, @PathVariable("matchNo") int matchNo) throws SQLException {
        try {
            Keyspace.setKeyspaceName(season);
            MatchStatistics matchStatistics = matchStatisticsService.getMatchStatistics(matchNo, season);
            MatchData matchData = matchDetailsService.getMatchDetails(matchNo, season);

            MatchInfoDTO matchInfoDTO = new MatchInfoDTO();
            matchInfoDTO.setMatchStatistics(matchStatistics);
            matchInfoDTO.setMatchData(matchData);

            return ResponseEntity.ok(matchInfoDTO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    @GetMapping("/{matchNo}/commentary")
    public ResponseEntity<MatchCommentsDTO> getComments(@PathVariable("season") int season, @PathVariable("matchNo") int matchNo) throws SQLException {
        try {
            Keyspace.setKeyspaceName(season);
            MatchStatistics matchStatistics = matchStatisticsService.getMatchStatistics(matchNo, season);
            List<List<MatchComments>> list = matchCommentService.getComments(matchNo, season);

            MatchCommentsDTO matchCommentsDTO = new MatchCommentsDTO();
            matchCommentsDTO.setMatchStatistics(matchStatistics);
            matchCommentsDTO.setMatchComments(list);

            return ResponseEntity.ok(matchCommentsDTO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    @GetMapping("{matchNo}/scorecard")
    public ResponseEntity<ScorecardDTO> getBatterScorecard(@PathVariable("season") int season, @PathVariable("matchNo") int matchNo) throws SQLException, IOException, URISyntaxException {
        return getScorecard(season, matchNo);
    }

    @GetMapping("/{matchNo}/points-table")
    public ResponseEntity<PointsTableDTO> getPointsTable(@PathVariable("season") int season, @PathVariable("matchNo") int matchNo) throws SQLException {
        try {
            Keyspace.setKeyspaceName(season);
            MatchStatistics matchStatistics = matchStatisticsService.getMatchStatistics(matchNo, season);
            List<PointsTableData> list = pointsTableService.getPointsTable(season);

            PointsTableDTO pointsTableDTO = new PointsTableDTO();
            pointsTableDTO.setMatchStatistics(matchStatistics);
            pointsTableDTO.setPointsTableData(list);

            return ResponseEntity.ok(pointsTableDTO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    @GetMapping("/{matchNo}/squads")
    public ResponseEntity<MatchSquadDTO> getSquad(@PathVariable("season") int season, @PathVariable("matchNo") int matchNo) throws SQLException {
        try {
            Keyspace.setKeyspaceName(season);
            MatchStatistics matchStatistics = matchStatisticsService.getMatchStatistics(matchNo, season);
            List<List<String>> list = playerSquadService.getSquad(matchNo, season);

            MatchSquadDTO matchSquadDTO = new MatchSquadDTO();
            matchSquadDTO.setMatchStatistics(matchStatistics);
            matchSquadDTO.setSquads(list);

            return ResponseEntity.ok(matchSquadDTO);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

//    @GetMapping("/points-table")
//    public ModelAndView getPointsTable(@PathVariable("season") int season) {
//        if(season == 2022) Keyspace.setSeason22();
//        else Keyspace.setSeason23();
//        ModelAndView mv = new ModelAndView();
//        MatchStatistics matchStatistics = new MatchStatistics();
//        List<PointsTableData> list = pointsTableService.getPointsTable(season);
//        mv.addObject("statistics", matchStatistics);
//        mv.addObject("data", list);
//        mv.setViewName("pointstable");
//        return mv;
//    }
}
