<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cricketapp.Model.MatchStatistics" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>Matches</title>
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
<div style="background-color: rgb(236, 240, 241);">
    <br>
    <br>
    <!-- Main content of the website goes here -->
    <div class="dm-content">
        <h6> IPL 2023 Results </h6>
    </div>

    <div class="dm-matches-div">
        <div class="ds-flex ds-flex-column ds-items-center ds-justify-start ds-justify-between">
            <% List<MatchStatistics> statistics = (List<MatchStatistics>) request.getAttribute("statistics");
            if (statistics != null) {
                for (MatchStatistics stats : statistics) { %>
                    <div class="ds-flex ds-flex-column ds-gap">
                        <div class="date-top"><%= stats.getDate() %></div>
                        <a href="/ipl2023/matches/<%= stats.getMatchNumber() %>" style="cursor: pointer; text-decoration: none; color: black;">
                            <div class="ds-flex ds-flex-column ds-gap bg-color-blue dm-ds-pd">
                                <p style="font-size: 15px; opacity:80%;">
                                    <%= stats.getMatchNumber() %><%= (stats.getMatchNumber() % 10 == 1) ? "st" : ((stats.getMatchNumber() % 10 == 2) ? "nd" : ((stats.getMatchNumber() % 10 == 3) ? "rd" : "th")) %> Match,
                                    <%= stats.getLocation() %>, <%= stats.getVenue() %>
                                </p>
                                <div style="margin-left: 50px; margin-right: 75px" class="ds-flex ds-flex-column ds-gap">
                                    <%
                                      String team1 = stats.getTeam1();
                                      String team2 = stats.getTeam2();
                                      String winner = stats.getWinner();
                                      String fontWeight1 = team1.equals(winner) ? "bold" : "normal";
                                      String fontWeight2 = team2.equals(winner) ? "bold" : "normal";
                                    %>

                                    <div class="ds-flex ds-flex-row ds-justify-between" style="font-weight:<%= fontWeight1 %>;">
                                        <div>
                                            <!-- <img width="30" height="23" alt="Chennai Super Kings Flag" class="ds-mr-2" src="https://img1.hscicdn.com/image/upload/f_auto,t_ds_square_w_160,q_50/lsci/db/PICTURES/CMS/313400/313421.logo.png" style="width: 20px; height: 20px;">  -->
                                            <%= stats.getTeam1() %>
                                        </div>
                                        <div><%= stats.getTeam1Score() %>/<%= stats.getTeam1Wickets() %></div>
                                    </div>
                                    <div class="ds-flex ds-flex-row ds-justify-between" style="font-weight:<%= fontWeight2 %>;">
                                        <div><%= stats.getTeam2() %></div>
                                        <div>
                                            <span style="opacity: 50%; font-weight: normal">(overs: <%= stats.getTeam2Overs() %>/<%= stats.getTeam2Overs() < 20.0? (stats.getTeam2Wickets() < 10? (stats.getTeam2Score() < stats.getTeam1Score()? stats.getTeam2Overs() : 20) : 20) : 20 %>)</span>
                                            <%= stats.getTeam2Score() %>/<%= stats.getTeam2Wickets() %>
                                        </div>
                                    </div>
                                    <div class="ds-flex ds-justify-center">
                                        <% if(!stats.getWinner().equals("No Result")) { %>
                                            <p><%= stats.getWinner() %> won by <%= stats.getWinnerRuns()==0? "" : (stats.getWinnerRuns() + " runs") %><%= stats.getWinnerWickets()==0? "" : (stats.getWinnerWickets() + " wickets")%> <%= stats.getTeam2Overs() < 20.0? (stats.getTeam2Wickets() < 10? (stats.getTeam2Score() < stats.getTeam1Score()? "(DLS Method)" : "") : "") : "" %></p>
                                        <% }
                                        else { %>
                                        <p> No Result </p>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                    <br>
            <%  }
            } %>
        </div>
    </div>
</div>
</body>
</html>
