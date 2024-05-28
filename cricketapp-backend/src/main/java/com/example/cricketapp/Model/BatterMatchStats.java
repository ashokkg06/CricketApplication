package com.example.cricketapp.Model;

public class BatterMatchStats {
    private String batter;
    private int matches;
    private int innings;
    private int runs;
    private Long ballsFaced;
    private Long fours;
    private Long sixes;
    private double strikeRate;
    private String comment;

    public String getBatter() {
        return batter;
    }

    public void setBatter(String batter) {
        this.batter = batter;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getInnings() {
        return innings;
    }

    public void setInnings(int innings) {
        this.innings = innings;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public long getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(Long ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public long getFours() {
        return fours;
    }

    public void setFours(Long fours) {
        this.fours = fours;
    }

    public long getSixes() {
        return sixes;
    }

    public void setSixes(Long sixes) {
        this.sixes = sixes;
    }

    public double getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(double strikeRate) {
        this.strikeRate = strikeRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "BatterMatchStats{" +
                "batter='" + batter + '\'' +
                ", matches=" + matches +
                ", innings=" + innings +
                ", runs=" + runs +
                ", ballsFaced=" + ballsFaced +
                ", fours=" + fours +
                ", sixes=" + sixes +
                ", strikeRate=" + strikeRate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
