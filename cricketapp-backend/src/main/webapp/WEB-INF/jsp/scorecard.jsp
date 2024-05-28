<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cricketapp.Model.MatchStatistics" %>
<%@ page import="com.example.cricketapp.Model.BatterMatchStats" %>
<%@ page import="com.example.cricketapp.Model.BowlerMatchStats" %>
<%@ page import="com.example.cricketapp.Model.ExtrasData" %>
<%@ page import="com.example.cricketapp.Model.TotalData" %>
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
                                    <% if(!stats.getWinner().equals("No Result")) { %>
                                        <p><%= stats.getWinner() %> won by <%= stats.getWinnerRuns()==0? "" : (stats.getWinnerRuns() + " runs") %><%= stats.getWinnerWickets()==0? "" : (stats.getWinnerWickets() + " wickets")%> <%= stats.getTeam2Overs() < 20.0? (stats.getTeam2Wickets() < 10? (stats.getTeam2Score() < stats.getTeam1Score()? "(DLS Method)" : "") : "") : "" %></p>
                                    <% }
                                    else { %>
                                    <p> No Result </p>
                                    <% } %>
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
                         <li class="ds-current-page"><a href="/ipl2023/matches/<%= matchNo %>/scorecard"> SCORECARD </a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/points-table">TABLE</a></li>
                         <li><a href="/ipl2023/matches/<%= matchNo %>/squads">SQUADS</a></li>
                     </ul>
                 </nav>
             </header>
        </div>
    </div>
    <br>

    <div class="body-margin">
        <% MatchStatistics match = (MatchStatistics) request.getAttribute("statistics");
        if (match != null) {
            String[] teams = new String[2];
            teams[0]  = match.getTeam1();
            teams[1] = match.getTeam2();

            MatchStatistics dbData = (MatchStatistics) request.getAttribute("statistics");
            List<TotalData> dataList = new ArrayList<>();

            TotalData td1 = new TotalData();
            td1.setOvers(dbData.getTeam1Overs());
            td1.setScore(dbData.getTeam1Score());
            td1.setWickets(dbData.getTeam1Wickets());
            dataList.add(td1);

            TotalData td2 = new TotalData();
            td2.setOvers(dbData.getTeam2Overs());
            td2.setScore(dbData.getTeam2Score());
            td2.setWickets(dbData.getTeam2Wickets());
            dataList.add(td2);

            List<List<BatterMatchStats>> battingRecord = (List<List<BatterMatchStats>>) request.getAttribute("batting");
            List<List<BowlerMatchStats>> bowlingRecord = (List<List<BowlerMatchStats>>) request.getAttribute("bowling");
            List<ExtrasData> extrasList = (List<ExtrasData>) request.getAttribute("extras");
            HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("dismissals");
            for(int i=0; i<extrasList.size(); i++) {
                List<BatterMatchStats> batting = battingRecord.get(i);
                List<BowlerMatchStats> bowling = bowlingRecord.get(i);
                ExtrasData extras = extrasList.get(i);
                TotalData data = dataList.get(i);
        %>
        <div class="ds-box">
            <div class="ds-flex ds-py-3 bg-blue">
                <div class="ds-flex ds-flex-col ds-grow ds-justify-center">
                    <span class="ds-text-title-ys ds-font-bold ds-capitalize ds-font-large"> <%= teams[i] %> </span>
                </div>
            </div>
            <div class="ds-text-title-xs border-b border-solid ds-bg-bordergrey">
                <div class="ds-grid p-2 ds-grid-one-sizes bg-lightgreen">
                    <div class="ds-font-bold"> BATTING </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> &nbsp; </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> R </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> B </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> 4s </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> 6s </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> SR </div>
                </div>
                <% for(BatterMatchStats bm: batting) {
                    String str = bm.getBatter();
                    str = str.replace(" ","-");
                %>
                <div class="ds-grid p-2 ds-grid-one-sizes ds-bg-bt">
                    <a href="/ipl2023/playerstats/<%=str%>" class="ds-text-dec-none"> <%= bm.getBatter() %> </a>
                    <div class="ds-flex ds-op-5"> <%= map.get(bm.getBatter())==null? "not out" : map.get(bm.getBatter()) %></div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getRuns() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getBallsFaced() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getFours() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getSixes() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getStrikeRate() %> </div>
                </div>
                <% } %>

                <div class="ds-grid p-2 ds-grid-one-sizes items-center ds-bg-bt">
                    <div class="ds-font-bold"> Extras </div>
                    <div class="ds-op-5 ">
                        <span> (b <%= extras.getByes() %>,
                                lb <%= extras.getLegByes() %>,
                                wd <%= extras.getWides() %>,
                                nb <%= extras.getNoBalls() %> )
                        </span>
                    </div>
                    <div class="ds-flex ds-justify-center items-center"> <span class="ds-font-bold"> <%= extras.getNoBalls()+extras.getLegByes()+extras.getWides()+extras.getByes() %> </span> </div>
                </div>

                <div class="ds-grid p-2 ds-grid-three-sizes items-center">
                    <div class="ds-font-bold"> TOTAL </div>
                    <div class="ds-op-5"> <%= data.getOvers() %> Overs (RR : <%= String.format("%.1f",data.getScore()/data.getOvers())%>)</div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> <%= data.getScore() %></div>
                </div>

            </div>
            <br>
            <div class="ds-text-title-xs border-b border-solid ds-bg-bordergrey">
                <div class="ds-grid p-2 ds-grid-two-sizes bg-lightgreen">
                    <div class="ds-font-bold"> BOWLING </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> O </div>
                    <!--<div class="ds-flex ds-justify-center items-center ds-font-bold"> M </div>-->
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> R </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> W </div>
                    <!--<div class="ds-flex ds-justify-center items-center ds-font-bold"> NB </div>
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> WD </div>-->
                    <div class="ds-flex ds-justify-center items-center ds-font-bold"> ECO </div>
                </div>
                <% for(BowlerMatchStats bm: bowling) {
                    String str = bm.getName();
                    str = str.replace(" ","-");
                %>
                <div class="ds-grid p-2 ds-grid-two-sizes ds-bg-bt">
                    <a href="/ipl2023/playerstats/<%=str%>" class="ds-text-dec-none"> <%= bm.getName() %> </a>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getOvers() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getRuns() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getWickets() %> </div>
                    <div class="ds-flex ds-justify-center items-center"> <%= bm.getEconomy() %> </div>
                </div>
                <% } %>
            </div>
            <br>
            <% }
            } %>
        </div>
    </div>
    <br>
</div>
</body>
</html>
