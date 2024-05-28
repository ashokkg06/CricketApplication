package com.example.cricketapp.Model;

public class MatchData {
    private int season;
    private String Date;
    private int matchNumber;
    private String stadium;
    private String location;
    private String team1;
    private String team2;
    private String tossWinner;
    private String tossDecision;
    private String umpire1;
    private String umpire2;
    private String reserveUmpire;
    private String referee;
    private String winner;
    private String manOfMatch;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getTossDecision() {
        return tossDecision;
    }

    public void setTossDecision(String tossDecision) {
        this.tossDecision = tossDecision;
    }

    public String getUmpire1() {
        return umpire1;
    }

    public void setUmpire1(String umpire1) {
        this.umpire1 = umpire1;
    }

    public String getUmpire2() {
        return umpire2;
    }

    public void setUmpire2(String umpire2) {
        this.umpire2 = umpire2;
    }

    public String getReserveUmpire() {
        return reserveUmpire;
    }

    public void setReserveUmpire(String reserveUmpire) {
        this.reserveUmpire = reserveUmpire;
    }

    public String getReferee() {
        return referee;
    }

    public void setRefree(String referee) {
        this.referee = referee;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getManOfMatch() {
        return manOfMatch;
    }

    public void setManOfMatch(String manOfMatch) {
        this.manOfMatch = manOfMatch;
    }

    @Override
    public String toString() {
        return "MatchData{" +
                "season=" + season +
                ", Date='" + Date + '\'' +
                ", matchNumber='" + matchNumber + '\'' +
                ", stadium='" + stadium + '\'' +
                ", location='" + location + '\'' +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", tossWinner='" + tossWinner + '\'' +
                ", tossDecision='" + tossDecision + '\'' +
                ", umpire1='" + umpire1 + '\'' +
                ", umpire2='" + umpire2 + '\'' +
                ", reserveUmpire='" + reserveUmpire + '\'' +
                ", refree='" + referee + '\'' +
                ", winner='" + winner + '\'' +
                ", manOfMatch='" + manOfMatch + '\'' +
                '}';
    }

    public String getShortTeam1() {
        return getShort(team1);
    }

    public String getShortTeam2() {
        return getShort(team2);
    }

    public String getShort(String team) {
        return switch (team.toLowerCase()) {
            case "gujarat titans" -> "GT";
            case "lucknow super giants" -> "LSG";
            case "chennai super kings" -> "CSK";
            case "mumbai indians" -> "MI";
            case "rajasthan royals" -> "RR";
            case "punjab kings" -> "PBKS";
            case "kolkata knight riders" -> "KKR";
            case "delhi capitals" -> "DC";
            case "sunrisers hyderabad" -> "SRH";
            case "royal challengers bangalore" -> "RCB";
            default -> "Unknown";
        };
    }
}
