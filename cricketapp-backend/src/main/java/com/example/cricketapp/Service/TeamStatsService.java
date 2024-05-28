package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.TeamStatsDAO;
import com.example.cricketapp.Model.TeamStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamStatsService {
    @Autowired
    TeamStatsDAO teamStatsDAO;

    @Cacheable(value = "teamstats", key = "#season + '-' + #teamName")
    public TeamStats getTeamStats(String teamName, int season) {
        return teamStatsDAO.getTeamStats(teamName, season);
    }
}
