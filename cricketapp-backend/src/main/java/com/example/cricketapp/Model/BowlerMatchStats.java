package com.example.cricketapp.Model;

public class BowlerMatchStats {
    private String name;
    private int matchNumber;
    private int innings;
    private int wickets;
    private Double overs;
    private int runs;
    private Double economy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getInnings() {
        return innings;
    }

    public void setInnings(int innings) {
        this.innings = innings;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
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

    public Double getEconomy() {
        return economy;
    }

    public void setEconomy(Double economy) {
        this.economy = economy;
    }

    @Override
    public String toString() {
        return "BowlerMatchStats{" +
                "name='" + name + '\'' +
                ", matchNumber=" + matchNumber +
                ", innings=" + innings +
                ", wickets=" + wickets +
                ", overs=" + overs +
                ", runs=" + runs +
                ", economy=" + economy +
                '}';
    }
}
