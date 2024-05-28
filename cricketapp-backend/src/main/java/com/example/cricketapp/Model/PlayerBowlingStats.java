package com.example.cricketapp.Model;

public class PlayerBowlingStats {
    private String bowlerName;
    private int matches;
    private int totalWickets;
    private Double overs;
    private int runs;
    private Double averageEconomy;

    public String getBowlerName() {
        return bowlerName;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getTotalWickets() {
        return totalWickets;
    }

    public void setTotalWickets(int totalWickets) {
        this.totalWickets = totalWickets;
    }

    public Double getOvers() {
        return overs;
    }

    public void setOvers(Double overs) {
        this.overs = overs;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public Double getAverageEconomy() {
        return averageEconomy;
    }

    public void setAverageEconomy(Double averageEconomy) {
        this.averageEconomy = averageEconomy;
    }

    @Override
    public String toString() {
        return "PlayerBowlingStats{" +
                "bowlerName='" + bowlerName + '\'' +
                ", matches=" + matches +
                ", totalWickets=" + totalWickets +
                ", overs=" + overs +
                ", runs=" + runs +
                ", averageEconomy=" + averageEconomy +
                '}';
    }
}
