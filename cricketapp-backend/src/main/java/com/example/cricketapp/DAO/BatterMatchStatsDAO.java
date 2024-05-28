package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.Model.BatterMatchStats;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BatterMatchStatsDAO {

    public List<List<BatterMatchStats>> getBatterMatchStats(int matchNo) {
        List<List<BatterMatchStats>> result = new ArrayList<>();

        try (CqlSession session = CqlSession.builder().withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).build()) {
            String query = "SELECT batter, MIN(getDismissalInfo(comment)) as dismissal, SUM(get_batter_run(outcome, score)) AS Runs, COUNT(isBall(outcome)) as Ballsfaced, COUNT(getFoursCount(outcome, score)) As fours, COUNT(getSixesCount(score)) as sixes, CAST(SUM(get_batter_run(outcome, score)) AS double) / (CAST(COUNT(isBall(outcome)) AS double)) * 100 AS Strike_Rate FROM batters_view WHERE match_no = ? group by batter ALLOW FILTERING;";
            PreparedStatement statement = session.prepare(query);
            ResultSet rs = session.execute(statement.bind(matchNo));
            System.out.println("QUERY EXECUTED: " + query);

            HashMap<String, BatterMatchStats> map1 = new HashMap<>();

            for(Row row: rs) {
                BatterMatchStats batterMatchStats = new BatterMatchStats();
                batterMatchStats.setBatter(row.getString("batter"));
                batterMatchStats.setComment(row.getString("dismissal"));
                batterMatchStats.setRuns(row.getInt("runs"));
                batterMatchStats.setBallsFaced(row.getLong("ballsfaced"));
                batterMatchStats.setFours(row.getLong("fours"));
                batterMatchStats.setSixes(row.getLong("sixes"));
                batterMatchStats.setStrikeRate(Double.parseDouble(String.format("%.2f", row.getDouble("strike_rate"))));
                map1.put(batterMatchStats.getBatter(), batterMatchStats);
            }

            for(int i=1; i<=2; i++) {
                query = "select batter from ballrecord where match_no=? and inningno=? group by ballnumber, batter;";
                statement = session.prepare(query);
                rs = session.execute(statement.bind(matchNo, i));
                System.out.println("QUERY EXECUTED: " + query);

                LinkedHashSet<String> set = new LinkedHashSet<String>();
                List<BatterMatchStats> list = new ArrayList<>();

                for (Row row : rs) {
                    set.add(row.getString("batter"));
                }

                for (String batter : set) {
                    list.add(map1.get(batter));
                }

                result.add(list);
            }
        }
        return result;
    }
}