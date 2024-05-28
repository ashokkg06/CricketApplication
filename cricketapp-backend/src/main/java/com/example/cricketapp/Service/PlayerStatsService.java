package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.PlayerStatsDAO;
import com.example.cricketapp.Model.PlayerBattingStats;
import com.example.cricketapp.Model.PlayerBowlingStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatsService {

    @Autowired
    PlayerStatsDAO playerStatsDAO;

    public boolean isBatter(String name) {
        return playerStatsDAO.isBatter(name);
    }

    public boolean isBowler(String name) {
        return playerStatsDAO.isBowler(name);
    }

    @Cacheable(value = "battingstats", key = "#season + '-' + #name")
    public PlayerBattingStats getPlayerBattingStats(String name, int season) {
        return playerStatsDAO.getPlayerBattingStats(name);
    }

    @Cacheable(value = "bowlingstats", key = "#season + '-' + #name")
    public PlayerBowlingStats getPlayerBowlingStats(String name, int season) {
        return playerStatsDAO.getPlayerBowlingStats(name);
    }
}
