package com.example.cricketapp.DTO;

import com.example.cricketapp.Model.PlayerBattingStats;
import com.example.cricketapp.Model.PlayerBowlingStats;

public class PlayerStatsDTO {
    private PlayerBattingStats playerBattingStats;
    private PlayerBowlingStats playerBowlingStats;

    public PlayerStatsDTO() {
    }

    public PlayerStatsDTO(PlayerBattingStats playerBattingStats, PlayerBowlingStats playerBowlingStats) {
        this.playerBattingStats = playerBattingStats;
        this.playerBowlingStats = playerBowlingStats;
    }

    public void setPlayerBattingStats(PlayerBattingStats playerBattingStats) {
        this.playerBattingStats = playerBattingStats;
    }

    public void setPlayerBowlingStats(PlayerBowlingStats playerBowlingStats) {
        this.playerBowlingStats = playerBowlingStats;
    }

    public PlayerBattingStats getPlayerBattingStats() {
        return playerBattingStats;
    }

    public PlayerBowlingStats getPlayerBowlingStats() {
        return playerBowlingStats;
    }
}
