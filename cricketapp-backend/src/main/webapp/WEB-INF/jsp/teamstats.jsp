<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cricketapp.Model.TeamStats" %>
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
            <div class="ds-box">

                <div class="ds-flex ds-py-3 bg-blue">
                    <div class="ds-flex ds-flex-col ds-grow ds-justify-center">
                        <% String teamName =  String.valueOf(request.getAttribute("teamName")).replace("-"," "); %>
                        <span class="ds-text-title-ys ds-font-bold ds-capitalize ds-font-large">
                            <%=teamName%>
                        </span>
                    </div>
                </div>

                <div class="w-full ds-flex ds-text-title-xs border-b border-solid ds-bg-bordergrey">
                    <div class="w-1/2  p-8 ds-font-medium">
                        <div class="p-2 ds-border-bt"> Number of Matches </div>
                        <div class="p-2 ds-border-bt"> Number of Matches Won </div>
                        <div class="p-2 ds-border-bt"> Number of Matches Lost </div>
                        <div class="p-2 ds-border-bt"> Number of Matches Tied </div>
                        <div class="p-2 ds-border-bt"> Number of Matches No Result </div>
                        <div class="p-2 ds-border-bt"> Number of Runs Scored </div>
                        <div class="p-2 ds-border-bt"> Number of Runs conceded </div>
                        <div class="p-2 ds-border-bt"> Number of Wickets Taken  </div>
                        <div class="p-2 ds-border-bt"> Number of Wickets lost </div>
                        <div class="p-2 ds-border-bt"> Position in Points Table </div>
                    </div>

                    <% TeamStats team = (TeamStats) request.getAttribute("team"); %>
                    <div class="w-1/2  p-8 ds-font-medium">
                        <div class="p-2 ds-border-bt"> <%= team.getMatches() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getWon() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getLost() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getTied() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getNoResult() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getRunsScored() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getRunsConceded() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getWicketsTaken() %></div>
                        <div class="p-2 ds-border-bt"> <%= team.getWicketsLost() %></div>
                        <div class="p-2 ds-border-bt">
                            <%= team.getPointsTablePosition() %><%= (team.getPointsTablePosition() % 10 == 1) ? "st" : ((team.getPointsTablePosition() % 10 == 2) ? "nd" : ((team.getPointsTablePosition() % 10 == 3) ? "rd" : "th")) %>
                        </div>
                    </div>
                <br>
                </div>
            </div>
        </div>
</body>
</html>