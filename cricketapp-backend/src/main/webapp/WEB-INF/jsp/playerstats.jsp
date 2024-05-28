<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cricketapp.Model.PlayerBattingStats" %>
<%@ page import="com.example.cricketapp.Model.PlayerBowlingStats" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/stylessheet.css">
        <title>Player Stats</title>
</head>
<body>
    <header class="sticky-header">
        <nav>
            <ul>
                <li><a href="/ipl2023/matches">HOME</a></li>
                <li><a href="/ipl2023/teams">TEAMS</a></li>
                <li><a href="/ipl2023/points-table">POINTS TABLE</a></li>
            </ul>
        </nav>
    </header>
    <div class="body-margin-p">
        <h3 style="text-decoration: underline;"> <%= ((String) request.getAttribute("name")).toUpperCase() %> in IPL 2023 </h3>
        <%
            PlayerBattingStats batter = (PlayerBattingStats) request.getAttribute("batter");
        if(batter!=null) { %>
        <div class="ds-box">
            <div class="ds-flex ds-py-3 bg-blue">
                <div class="ds-flex ds-flex-col ds-grow ds-justify-center">
                    <span class="ds-text-title-ys ds-font-bold ds-capitalize ds-font-large"> Batting Stats </span>
                </div>
            </div>
            <div class="ds-text-title-xs border-b border-solid ds-bg-bordergrey">
                <div class="ds-grid p-2 ds-grid-sizes-25-even bg-lightgreen">
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Matches </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Runs </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Average </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Highest Score </div>
                </div>

                <div class="ds-grid p-2 ds-grid-sizes-25-even">
                    <div class="ds-flex ds-justify-center items-center"> <%= batter.getMatches() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= batter.getRuns() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= batter.getAverage() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= batter.getHighestScore() %> </div>
                </div>
            </div>
        </div>
        <% } %>
        <br>

        <% PlayerBowlingStats bowler = (PlayerBowlingStats) request.getAttribute("bowler");
        if(bowler!=null) {%>
        <div class="ds-box">
            <div class="ds-flex ds-py-3 bg-blue">
                <div class="ds-flex ds-flex-col ds-grow ds-justify-center">
                    <span class="ds-text-title-ys ds-font-bold ds-capitalize ds-font-large"> Bowling Stats </span>
                </div>
            </div>
            <div class="ds-text-title-xs border-b border-solid ds-bg-bordergrey">
                <div class="ds-grid p-2 ds-grid-sizes-20-even bg-lightgreen">
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Matches </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Wickets </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Overs </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Runs </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> Avg Economy </div>
                </div>

                <div class="ds-grid p-2 ds-grid-sizes-20-even">
                    <div class="ds-flex ds-justify-center items-center"> <%= bowler.getMatches() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bowler.getTotalWickets() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bowler.getOvers() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bowler.getRuns() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bowler.getAverageEconomy() %> </div>
                </div>
            </div>
        </div>
        <% } %>

    </div>
</body>
</html>