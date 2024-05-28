<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cricketapp.Model.MatchStatistics" %>
<%@ page import="com.example.cricketapp.Model.MatchComments" %>
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
        <% MatchStatistics stats = (MatchStatistics) request.getAttribute("statistics");
        if (stats.getDate() != null) { %>
        <div class="ds-flex ds-flex-column ds-items-center ds-justify-start ds-justify-between ds-pd">
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
        </div>
        <div>
             <header class="sticky-headers">
                 <nav class="ds-border-top">
                     <ul>
                         <% int matchNo = (int) request.getAttribute("matchNo"); %>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/info">INFO</a></li>
                         <li class="ds-current-page"><a href="/ipl2023/matches/<%= matchNo %>/commentary">COMMENTARY</a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/scorecard">SCORECARD</a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/points-table">TABLE</a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/squads">SQUADS</a></li>
                     </ul>
                 </nav>
             </header>
        </div>
        <% } %>
    </div>
    <br>

    <div class="body-margin">
        <div class="ds-box">
            <div class="ds-text-title-xs border-b border-solid ds-bg-bordergrey ds-bg-bt">
                <% MatchStatistics match = (MatchStatistics) request.getAttribute("statistics"); %>
                <div class="ds-flex ds-justify-center items-center p-1-rem ds-bg-bt gap">
                    <% String phrase = match.getWinner().equals(match.getTeam1())? ("Wow, What a Defend!, ") : ("Wow, What a chase we are witnessed!, "); %>
                    <%=phrase%>
                    <span class="ds-font-bold"> <%= match.getWinner() + " " %></span> won by <%= match.getWinnerRuns()==0? "" : (match.getWinnerRuns() + " runs.") %><%= match.getWinnerWickets()==0? "" : (match.getWinnerWickets() + " wickets.")%> <%= match.getTeam2Overs() < 20.0? (match.getTeam2Wickets() < 10? (match.getTeam2Score() < match.getTeam1Score()? "(DLS Method)." : "") : "") : "" %>
                </div>
                <% List<List<MatchComments>> list = (List<List<MatchComments>>) request.getAttribute("commentary");
                boolean firstInning = true;
                for(List<MatchComments> comments: list) {
                    for(MatchComments comment: comments) {
                    %>
                    <div class="ds-grid p-2 ds-grid-comment ds-bg-bt">
                    <div class="ds-flex ds-justify-center items-center p-75-rem"> <%= comment.getOvers() %> </div>
                    <% String str = comment.getOutcome().equals("4")? "ds-boundary-4" : (comment.getOutcome().equals("6")? "ds-boundary-6" : (comment.getOutcome().equals("w")? "ds-boundary-w" : "ds-boundary-1"));
                    String outcome = (comment.getComment().endsWith("no run,") || comment.getComment().endsWith("no ball,"))? "•" : comment.getOutcome();
                    %>
                    <div class="ds-flex ds-justify-center items-center ds-boundary ds-font-boundary ds-font-bold <%=str%> ds-w-10 ds-h-10"> <%= outcome %> </div>
                    <div class="p-75-rem"> <%= comment.getComment() %> </div>
                    </div>
                    <% }
                    if(firstInning) { %>
                        <div style="border-bottom: 1px solid; font-size: large;" class="ds-flex ds-justify-center items-center p-75-rem ds-gb-bt"> Innings Break </div>
                    <% firstInning=!firstInning;}
                } %>
            </div>
            <br>
        </div>
    </div>
    <br>
</div>
</body>
</html>
