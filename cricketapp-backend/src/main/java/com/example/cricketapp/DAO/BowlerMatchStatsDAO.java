package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.DBConnection;
import com.example.cricketapp.Model.BatterMatchStats;
import com.example.cricketapp.Model.BowlerMatchStats;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class BowlerMatchStatsDAO {

    public List<List<BowlerMatchStats>> getBowlerMatchStats(int matchNo) {
        List<List<BowlerMatchStats>> result = new ArrayList<>();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            for(int i=1; i<=2; i++) {
                String query = "SELECT bowler, SUM(get_wickets(outcome)) as Wickets, SUM(get_runs_given(outcome, score)) as runs_conceded, COUNT(get_overs(outcome))/6 + CAST(COUNT(get_overs(outcome))%6/10.0 as double) as overs_bowled, SUM(get_runs_given(outcome, score))/(COUNT(get_overs(outcome))/6.0) as economy from bowlers_view where match_no=? and inningno=? group by bowler ALLOW FILTERING;";
                PreparedStatement statement = session.prepare(query);
                ResultSet rs = session.execute(statement.bind(matchNo, i));
                System.out.println("QUERY EXECUTED: " + query);

                List<BowlerMatchStats> list = new ArrayList<>();

                for(Row row: rs) {
                    BowlerMatchStats bowlerMatchStats = new BowlerMatchStats();
                    bowlerMatchStats.setName(row.getString("bowler"));
                    bowlerMatchStats.setWickets(row.getInt("wickets"));
                    bowlerMatchStats.setOvers(row.getDouble("overs_bowled"));
                    bowlerMatchStats.setRuns(row.getInt("runs_conceded"));
                    bowlerMatchStats.setEconomy(Double.parseDouble(String.format("%.1f", row.getDouble("economy"))));
                    list.add(bowlerMatchStats);
                }
                result.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
