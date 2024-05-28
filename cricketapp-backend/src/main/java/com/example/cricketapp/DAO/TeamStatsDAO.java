package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.Model.PointsTableData;
import com.example.cricketapp.Model.TeamStats;
import com.example.cricketapp.Service.PointsTableService;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamStatsDAO {
    @Autowired
    PointsTableService pointsTableService;
    public TeamStats getTeamStats(String teamName, int season) {
        TeamStats teamStats = new TeamStats();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            System.out.println("team stats started");
            String query1 = "select count(match_number) as matches, count(get_winner(winner, ?)) as won, count(get_lose(winner, ?)) as lose, count(get_noresult(winner)) as NR, count(get_tie(winner)) as tie from matchrecord where team1=? ALLOW FILTERING;";
            PreparedStatement statement1 = session.prepare(query1);
            ResultSet rs1 = session.execute(statement1.bind(teamName, teamName, teamName));

            String query2 = "select count(match_number) as matches, count(get_winner(winner, ?)) as won, count(get_lose(winner, ?)) as lose, count(get_noresult(winner)) as NR, count(get_tie(winner)) as tie from matchrecord where team2=? ALLOW FILTERING;";
            PreparedStatement statement2 = session.prepare(query2);
            ResultSet rs2 = session.execute(statement2.bind(teamName, teamName, teamName));

            String query3 = "select SUM(getteamtotalscore(team1score, team2score, team1, team2, ?)) as teamscore, SUM(getopteamtotalscore(team1score, team2score, team1, team2, ?)) as opteamscore, SUM(getteamtotalwickets(team1wickets, team2wickets, team1, team2, ?)) as teamwickets, SUM(getopteamtotalwickets(team1wickets, team2wickets, team1, team2, ?)) as opteamwickets from teamrecord where team1=? ALLOW FILTERING;";
            PreparedStatement statement3 = session.prepare(query3);
            ResultSet rs3 = session.execute(statement3.bind(teamName, teamName, teamName, teamName, teamName));

            String query4 = "select SUM(getteamtotalscore(team1score, team2score, team1, team2, ?)) as teamscore, SUM(getopteamtotalscore(team1score, team2score, team1, team2, ?)) as opteamscore, SUM(getteamtotalwickets(team1wickets, team2wickets, team1, team2, ?)) as teamwickets, SUM(getopteamtotalwickets(team1wickets, team2wickets, team1, team2, ?)) as opteamwickets from teamrecord where team2=? ALLOW FILTERING;";
            PreparedStatement statement4 = session.prepare(query4);
            ResultSet rs4 = session.execute(statement4.bind(teamName, teamName, teamName, teamName, teamName));

            Row row1 = rs1.one(), row2 = rs2.one(), row3 = rs3.one(), row4 = rs4.one();

            teamStats.setMatches(row1.getLong("matches") + row2.getLong("matches"));
            teamStats.setWon(row1.getLong("won") + row2.getLong("won"));
            teamStats.setLost(row1.getLong("lose") + row2.getLong("lose"));
            teamStats.setNoResult(row1.getLong("NR") + row2.getLong("NR"));
            teamStats.setTied(row1.getLong("tie") + row2.getLong("tie"));
            teamStats.setRunsScored(row3.getInt("teamscore") + row4.getInt("teamscore"));
            teamStats.setRunsConceded(row3.getInt("opteamscore") + row4.getInt("opteamscore"));
            teamStats.setWicketsTaken(row3.getInt("teamwickets") + row4.getInt("teamwickets"));
            teamStats.setWicketsLost(row3.getInt("opteamwickets") + row4.getInt("opteamwickets"));

            System.out.println("team stats completed without points");
            List<PointsTableData> list = pointsTableService.getPointsTable(season);
            for(int i=0; i<list.size(); i++) {
                if(list.get(i).getTeam().equalsIgnoreCase(teamName)) {
                    teamStats.setPointsTablePosition(i+1);
                    break;
                }
            }
        }
        return teamStats;
    }
}
