<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cricketapp.Model.MatchStatistics" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/stylessheet.css">
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
<div style="background-color: rgb(236, 240, 241); ">
    <br>
    <br>
    <!-- Main content of the website goes here -->
    <div class="m-content body-margin">
        <h6> IPL 2023 Results </h6>
    </div>

    <div class="matches-div body-margin">
        <div class="ds-flex ds-flex-column ds-items-center ds-justify-start ds-justify-between ds-pd">
            <% MatchStatistics stats = (MatchStatistics) request.getAttribute("statistics");
            if (stats != null) { %>
                <div class="ds-flex ds-flex-column ds-gap">
                        <div class="ds-flex ds-flex-column ds-gap">
                            <p style="font-size: 15px; opacity:80%;">
                                <%= stats.getDate() %>,
                                <%= stats.getMatchNumber() %><%= (stats.getMatchNumber() % 10 == 1) ? "st" : ((stats.getMatchNumber() % 10 == 2) ? "nd" : ((stats.getMatchNumber() % 10 == 3) ? "rd" : "th")) %> Match,
                                <%= stats.getLocation() %>, <%= stats.getVenue() %>
                            </p>
                            <div style="margin-left: 50px; margin-right: 75px; line-height: 25px;" class="ds-flex ds-flex-column ds-gap">
                                <%
                                String team1 = stats.getTeam1();
                                String team2 = stats.getTeam2();
                                String winner = stats.getWinner();
                                String fontWeight1 = team1.equals(winner) ? "bold" : "normal";
                                String fontWeight2 = team2.equals(winner) ? "bold" : "normal";
                                %>

                                <div class="ds-flex ds-flex-row ds-justify-between" style="font-weight:<%= fontWeight1 %>;">
                                    <div>
                                        <%= stats.getTeam1() %>
                                    </div>
                                    <div><%= stats.getTeam1Score() %>/<%= stats.getTeam1Wickets() %></div>
                                </div>
                                <div class="ds-flex ds-flex-row ds-justify-between" style="font-weight:<%= fontWeight2 %>;">
                                    <div><%= stats.getTeam2() %></div>
                                    <div>
                                        <span style="opacity: 75%">(overs: <%= stats.getTeam2Overs() %>/<%= stats.getTeam2Overs() < 20.0? (stats.getTeam2Wickets() < 10? (stats.getTeam2Score() < stats.getTeam1Score()? stats.getTeam2Overs() : 20) : 20) : 20 %>)</span>
                                        <%= stats.getTeam2Score() %>/<%= stats.getTeam2Wickets() %>
                                    </div>
                                </div>
                                <div class="ds-flex ds-justify-center">
                                    <p><%= stats.getWinner() %> won by <%= stats.getWinnerRuns()==0? "" : (stats.getWinnerRuns() + " runs") %><%= stats.getWinnerWickets()==0? "" : (stats.getWinnerWickets() + " wickets")%> <%= stats.getTeam2Overs() < 20.0? (stats.getTeam2Wickets() < 10? (stats.getTeam2Score() < stats.getTeam1Score()? "(DLS Method)" : "") : "") : "" %></p>
                                </div>
                            </div>
                        </div>
                </div>
            <% } %>
        </div>

        <div>
             <header class="sticky-headers">
                 <nav class="ds-border-top">
                     <ul>
                         <% int matchNo = (int) request.getAttribute("matchNo"); %>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/info">INFO</a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/commentary">COMMENTARY</a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/scorecard">SCORECARD</a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/points-table">TABLE</a></li>
                         <li class="ds-current-page"><a href="/ipl2023/matches/<%= matchNo %>/squads">SQUADS</a></li>
                     </ul>
                 </nav>
             </header>
        </div>
    </div>
    <br>

    <div class="body-margin">
        <div class="ds-box">

            <div class="ds-flex ds-py-3 bg-blue">
                <div class="ds-flex ds-flex-col ds-grow ds-justify-center">
                    <% MatchStatistics team = (MatchStatistics) request.getAttribute("statistics"); %>
                    <span class="ds-text-title-ys ds-font-bold ds-capitalize ds-font-large">
                        Match Squad (<%= team.getShortTeam1() %> vs <%= team.getShortTeam2() %>)
                    </span>
                </div>
            </div>

            <div class="w-full ds-flex ds-text-title-xs border-b border-solid ds-bg-bordergrey">
                <% List<List<String>> list = (List<List<String>>) request.getAttribute("squad");
                    int i = 1;
                   for(List<String> players: list) {
                        MatchStatistics match = (MatchStatistics) request.getAttribute("statistics");
                   %>
                <div class="w-1/2  p-8 ds-font-medium">
                    <div class="p-2 bg-lightgreen">
                        <div class="ds-flex ds-justify-center items-center ds-font-bold"> <%= i==1? match.getTeam1(): match.getTeam2() %> </div>
                    </div>

                    <% for(String team1: players) {
                        String str = team1.replace(" ","-");
                        String border = i==1? "ds-border-bt": "ds-border-bt-rgt";
                    %>
                    <a class="ds-flex ds-justify-center items-center p-2 ds-text-dec-none <%= border %>" href="/ipl2023/playerstats/<%=str%>"> <%= team1 %> </a>
                    <% } %>
                </div>
                <%i=i+1;%>
                <% } %>
            </div>
            <br>
        </div>
    </div>
    <br>
</div>
</body>
</html>
