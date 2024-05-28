package com.example.cricketapp.Model;

public class PlayerBattingStats {
    private String batterName;
    private Long matches;
    private int runs;
    private Double average;
    private int highestScore;

    public String getBatterName() {
        return batterName;
    }

    public void setBatterName(String batterName) {
        this.batterName = batterName;
    }

    public Long getMatches() {
        return matches;
    }

    public void setMatches(Long matches) {
        this.matches = matches;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "batterName='" + batterName + '\'' +
                ", matches=" + matches +
                ", runs=" + runs +
                ", average=" + average +
                ", highestScore=" + highestScore +
                '}';
    }
}
