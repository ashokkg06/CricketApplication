<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cricketapp.Model.PointsTableData" %>
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
                    <span class="ds-text-title-ys ds-font-bold ds-capitalize ds-font-large">
                        IPL 2023 Teams
                    </span>
                </div>
            </div>



            <div class="w-full ds-flex ds-text-title-xs border-b border-solid ds-bg-bordergrey">
                <% List<PointsTableData> list = (List<PointsTableData>) request.getAttribute("teams"); %>
                <div class="w-1/2  p-8 ds-font-medium">

                    <% for(int i=0; i<5; i++) {
                        PointsTableData data = list.get(i);
                        String str1 = data.getTeam().replace(" ","-");
                    %>
                    <div class="ds-flex ds-justify-center items-center p-1-rem ds-border-bt ds-text-dec-none"><a class="ds-text-dec-none" href="/ipl2023/teams/<%=str1%>"> <%= data.getTeam() %> </a></div>
                    <% } %>
                </div>
                <div class="w-1/2  p-8 ds-font-medium">
                    <% for(int i=5; i<10; i++) {
                        PointsTableData data = list.get(i);
                        String str2 = data.getTeam().replace(" ","-");
                    %>
                    <div class="ds-flex ds-justify-center items-center p-1-rem ds-border-bt ds-text-dec-none"><a class="ds-text-dec-none" href="/ipl2023/teams/<%=str2%>"> <%= data.getTeam() %> </a></div>
                    <% } %>
                </div>
            </div>
            <br>
        </div>
    </div>
</body>
</html>