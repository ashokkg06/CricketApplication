package com.example.cricketapp.Model;

public class TotalData {
    private Double overs;
    private int score;
    private int wickets;

    public Double getOvers() {
        return overs;
    }

    public void setOvers(Double overs) {
        this.overs = overs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    @Override
    public String toString() {
        return "TotalData{" +
                "overs=" + overs +
                ", score=" + score +
                ", wickets=" + wickets +
                '}';
    }
}
