package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.example.cricketapp.Model.MatchStatistics;
import com.example.cricketapp.config.CassandraConfigLoader;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchStatisticsDAO {

    public List<MatchStatistics> getMatchStatistics() {
        List<MatchStatistics> list = new ArrayList<>();

        try (CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).build()) {
            for(int i=1; i<=75; i+=30) {
                StringBuilder sb = new StringBuilder();
                sb.append("(");
                for (int j = i; j <= i+29 && j<=75; j++) {
                    sb.append(j).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(")");
                String range = sb.toString();
                String query1 = "select * from matchrecord where match_number IN " + range + ";";
                String query2 = "SELECT match_no, SUM(get_score_1(score, inningno)) as teamscore1, SUM(get_score_2(score, inningno)) as teamscore2, SUM(get_wickets_1(outcome, inningno)) as teamwickets1,  SUM(get_wickets_2(outcome, inningno)) as teamwickets2 , COUNT(get_overs_1(outcome, inningno))/6 + CAST(COUNT(get_overs_1(outcome, inningno))%6/10.0 as double) as teamover1, COUNT(get_overs_2(outcome, inningno))/6 + CAST(COUNT(get_overs_2(outcome, inningno))%6/10.0 as double) as teamover2 from ballrecord where match_no IN " + range + " group by match_no ALLOW FILTERING;";
                System.out.println("QUERY1 EXECUTED: " + query1);
                System.out.println("QUERY2 EXECUTED: " + query2);
                SimpleStatement statement1 = SimpleStatement.builder(query1)
                        .setConsistencyLevel(ConsistencyLevel.LOCAL_ONE)
                        .build();
                SimpleStatement statement2 = SimpleStatement.builder(query2)
                        .setConsistencyLevel(ConsistencyLevel.LOCAL_ONE)
                        .build();
                ResultSet result1 = session.execute(statement1);
                ResultSet result2 = session.execute(statement2);
                List<Row> row2 = result2.all();
                int index = 0;
                for (Row rs1 : result1) {
                    MatchStatistics statistics = new MatchStatistics();
                    statistics.setDate(rs1.getString("date"));
                    statistics.setSeason(rs1.getInt("season"));
                    statistics.setMatchNumber(rs1.getInt("match_number"));
                    statistics.setVenue(rs1.getString("venue"));
                    statistics.setLocation(rs1.getString("location"));
                    statistics.setTeam1(rs1.getString("team1"));
                    statistics.setTeam2(rs1.getString("team2"));
                    statistics.setWinner(rs1.getString("winner"));
                    statistics.setWinnerRuns(rs1.getInt("winner_runs"));
                    statistics.setWinnerWickets(rs1.getInt("winner_wickets"));

                    Row rs2 = row2.get(index++);
                    statistics.setTeam1Score(rs2.getInt("teamscore1"));
                    statistics.setTeam1Wickets(rs2.getInt("teamwickets1"));
                    statistics.setTeam1Overs(rs2.getDouble("teamover1"));

                    statistics.setTeam2Score(rs2.getInt("teamscore2"));
                    statistics.setTeam2Wickets(rs2.getInt("teamwickets2"));
                    statistics.setTeam2Overs(rs2.getDouble("teamover2"));

                    list.add(statistics);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public MatchStatistics getMatchStatistics(int matchNo) {
        MatchStatistics statistics = new MatchStatistics();
        try (CqlSession session = CqlSession.builder().withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).build()) {
            String query1 = "select * from matchrecord where match_number=" + matchNo + ";";
            String query2 = "SELECT SUM(score) as team_score, SUM(get_wickets(outcome)) as team_wickets, COUNT(get_overs(outcome))/6 + CAST(COUNT(get_overs(outcome))%6/10.0 as double) as team_overs from ballrecord where match_no=" + matchNo + " group by match_no, inningno ALLOW FILTERING;";
            System.out.println("QUERY1 EXECUTED: " + query1);
            System.out.println("QUERY2 EXECUTED: " + query2);
            ResultSet result1 = session.execute(query1);
            ResultSet result2 = session.execute(query2);
            List<Row> rowList = result2.all();
            if (result1.getAvailableWithoutFetching() > 0) {
                Row rs1 = result1.one();
                statistics.setDate(rs1.getString("date"));
                statistics.setSeason(rs1.getInt("season"));
                statistics.setMatchNumber(rs1.getInt("match_number"));
                statistics.setVenue(rs1.getString("venue"));
                statistics.setLocation(rs1.getString("location"));
                statistics.setTeam1(rs1.getString("team1"));
                statistics.setTeam2(rs1.getString("team2"));
                statistics.setWinner(rs1.getString("winner"));
                statistics.setWinnerRuns(rs1.getInt("winner_runs"));
                statistics.setWinnerWickets(rs1.getInt("winner_wickets"));

                Row rs2 = rowList.get(0);
                statistics.setTeam1Score(rs2.getInt("team_score"));
                statistics.setTeam1Wickets(rs2.getInt("team_wickets"));
                statistics.setTeam1Overs(rs2.getDouble("team_overs"));

                rs2 = rowList.get(1);
                statistics.setTeam2Score(rs2.getInt("team_score"));
                statistics.setTeam2Wickets(rs2.getInt("team_wickets"));
                statistics.setTeam2Overs(rs2.getDouble("team_overs"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statistics;
    }
}