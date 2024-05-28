package com.example.cricketapp.Model;

public class MatchStatistics {
    private String date;
    private int season;
    private int matchNumber;
    private String venue;
    private String location;
    private String team1;
    private String team2;
    private String winner;
    private int team1Score;
    private int team2Score;
    private int team1Wickets;
    private int team2Wickets;
    private Double team1Overs;
    private Double team2Overs;
    private int winnerRuns;
    private int winnerWickets;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public int getTeam1Wickets() {
        return team1Wickets;
    }

    public void setTeam1Wickets(int team1Wickets) {
        this.team1Wickets = team1Wickets;
    }

    public int getTeam2Wickets() {
        return team2Wickets;
    }

    public void setTeam2Wickets(int team2Wickets) {
        this.team2Wickets = team2Wickets;
    }

    public Double getTeam1Overs() {
        return team1Overs;
    }

    public void setTeam1Overs(Double team1Overs) {
        this.team1Overs = team1Overs;
    }

    public Double getTeam2Overs() {
        return team2Overs;
    }

    public void setTeam2Overs(Double team2Overs) {
        this.team2Overs = team2Overs;
    }

    public int getWinnerRuns() {
        return winnerRuns;
    }

    public void setWinnerRuns(int winnerRuns) {
        this.winnerRuns = winnerRuns;
    }

    public int getWinnerWickets() {
        return winnerWickets;
    }

    public void setWinnerWickets(int winnerWickets) {
        this.winnerWickets = winnerWickets;
    }

    @Override
    public String toString() {
        return "MatchStatistics{" +
                "date='" + date + '\'' +
                ", season=" + season +
                ", matchNumber=" + matchNumber +
                ", venue='" + venue + '\'' +
                ", location='" + location + '\'' +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", winner='" + winner + '\'' +
                ", team1Score=" + team1Score +
                ", team2Score=" + team2Score +
                ", team1Wickets=" + team1Wickets +
                ", team2Wickets=" + team2Wickets +
                ", team1Overs=" + team1Overs +
                ", team2Overs=" + team2Overs +
                ", winnerRuns=" + winnerRuns +
                ", winnerWickets=" + winnerWickets +
                '}';
    }

    public String getShortTeam1() {
        return getShort(team1);
    }

    public String getShortTeam2() {
        return getShort(team2);
    }

    public String getShort(String team) {
        if(team==null) return "";
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

