package com.example.cricketapp.Model;

public class PointsTableData {
    private String team;
    private Long wins;
    private Long lose;
    private Long points;

    private Double netRunRate;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Long getWins() {
        return wins;
    }

    public void setWins(Long wins) {
        this.wins = wins;
    }

    public Long getLose() {
        return lose;
    }

    public void setLose(Long lose) {
        this.lose = lose;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Double getNetRunRate() {
        return netRunRate;
    }

    public void setNetRunRate(Double netRunRate) {
        this.netRunRate = netRunRate;
    }

    @Override
    public String toString() {
        return "PointsTableData{" +
                "team='" + team + '\'' +
                ", wins=" + wins +
                ", lose=" + lose +
                ", points=" + points +
                ", netRunRate=" + netRunRate +
                '}';
    }
}
