package com.example.cricketapp.Model;

public class TeamStats {
    private Long matches;
    private Long won;
    private Long lost;
    private Long NoResult;
    private Long tied;
    private int runsScored;
    private int runsConceded;
    private int wicketsTaken;
    private int wicketsLost;
    private int pointsTablePosition;

    public Long getMatches() {
        return matches;
    }

    public void setMatches(Long matches) {
        this.matches = matches;
    }

    public Long getWon() {
        return won;
    }

    public void setWon(Long won) {
        this.won = won;
    }

    public Long getLost() {
        return lost;
    }

    public void setLost(Long lost) {
        this.lost = lost;
    }

    public Long getNoResult() {
        return NoResult;
    }

    public void setNoResult(Long noResult) {
        NoResult = noResult;
    }

    public Long getTied() {
        return tied;
    }

    public void setTied(Long tied) {
        this.tied = tied;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getRunsConceded() {
        return runsConceded;
    }

    public void setRunsConceded(int runsConceded) {
        this.runsConceded = runsConceded;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public int getWicketsLost() {
        return wicketsLost;
    }

    public void setWicketsLost(int wicketsLost) {
        this.wicketsLost = wicketsLost;
    }

    public int getPointsTablePosition() {
        return pointsTablePosition;
    }

    public void setPointsTablePosition(int pointsTablePosition) {
        this.pointsTablePosition = pointsTablePosition;
    }

    @Override
    public String toString() {
        return "TeamStats{" +
                "matches=" + matches +
                ", won=" + won +
                ", lost=" + lost +
                ", NoResult=" + NoResult +
                ", tied=" + tied +
                ", runsScored=" + runsScored +
                ", runsConceded=" + runsConceded +
                ", wicketsTaken=" + wicketsTaken +
                ", wicketsGiven=" + wicketsLost +
                ", pointsTablePosition=" + pointsTablePosition +
                '}';
    }
}
