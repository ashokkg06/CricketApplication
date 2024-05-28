package com.example.cricketapp.Model;

import java.util.List;

public class BattingStats {
    private String batterName;
    private int matches;
    private int runs;
    private double average;
    private int highestScore;


    public String getBatterName() {
        return batterName;
    }

    public void setBatterName(String batterName) {
        this.batterName = batterName;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

//    public List<BattingStats> getBattingStatistics() {
//        String sql = "SELECT batter AS Batter_Name, " +
//                "COUNT(DISTINCT match_no) AS Matches, " +
//                "SUM(match_score) AS Runs, " +
//                "AVG(match_score) AS Average, " +
//                "MAX(match_score) AS HS " +
//                "FROM ( " +
//                "SELECT batter, match_no, " +
//                "SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like '%wd%' THEN score - 1 ELSE score END) AS match_score " +
//                "FROM ballrecord " +
//                "GROUP BY batter, match_no " +
//                ") AS match_scores " +
//                "GROUP BY batter";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            BattingStats statistics = new BattingStats();
//            statistics.setBatterName(rs.getString("Batter_Name"));
//            statistics.setMatches(rs.getInt("Matches"));
//            statistics.setRuns(rs.getInt("Runs"));
//            statistics.setAverage(rs.getDouble("Average"));
//            statistics.setHighestScore(rs.getInt("HS"));
//            return statistics;
//        });
//    }
}
