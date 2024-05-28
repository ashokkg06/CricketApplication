package com.example.cricketapp.Model;

public class ExtrasData {
    private int matchNumber;
    private int inningNumber;
    private Long noBalls;
    private int legByes;
    private int wides;
    private int byes;

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(int inningNumber) {
        this.inningNumber = inningNumber;
    }

    public Long getNoBalls() {
        return noBalls;
    }

    public void setNoBalls(Long noBalls) {
        this.noBalls = noBalls;
    }

    public int getLegByes() {
        return legByes;
    }

    public void setLegByes(int legByes) {
        this.legByes = legByes;
    }

    public int getWides() {
        return wides;
    }

    public void setWides(int wides) {
        this.wides = wides;
    }

    public int getByes() {
        return byes;
    }

    public void setByes(int byes) {
        this.byes = byes;
    }

    @Override
    public String toString() {
        return "ExtrasData{" +
                "matchNumber='" + matchNumber + '\'' +
                ", inningNumber='" + inningNumber + '\'' +
                ", noBalls=" + noBalls +
                ", legByes=" + legByes +
                ", wides=" + wides +
                ", byes=" + byes +
                '}';
    }
}
