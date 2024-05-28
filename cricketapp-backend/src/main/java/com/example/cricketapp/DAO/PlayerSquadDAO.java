package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.DBConnection;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class PlayerSquadDAO {
    public List<List<String>> getSquad(int matchNo) {
        List<List<String>> players = new ArrayList<>();
        List<String> squad1 = new ArrayList<>();
        List<String> squad2 = new ArrayList<>();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            String query1 = "select batter from batters_view where match_no=? and inningno=1 group by batter ALLOW FILTERING;";
            String query2 = "select bowler from bowlers_view where match_no=? and inningno=2 group by bowler ALLOW FILTERING;";

            PreparedStatement statement1 = session.prepare(query1);
            PreparedStatement statement2 = session.prepare(query2);

            ResultSet rs1 = session.execute(statement1.bind(matchNo));
            ResultSet rs2 = session.execute(statement2.bind(matchNo));

            LinkedHashSet<String> set1 = new LinkedHashSet<String>();
            for(Row row: rs1) set1.add(row.getString("batter"));
            for(Row row: rs2) set1.add(row.getString("bowler"));

            squad1.addAll(set1);

            String query3 = "select batter from batters_view where match_no=? and inningno=2 group by batter ALLOW FILTERING;";
            String query4 = "select bowler from bowlers_view where match_no=? and inningno=1 group by bowler ALLOW FILTERING;";

            PreparedStatement statement3 = session.prepare(query3);
            PreparedStatement statement4 = session.prepare(query4);

            ResultSet rs3 = session.execute(statement3.bind(matchNo));
            ResultSet rs4 = session.execute(statement4.bind(matchNo));

            LinkedHashSet<String> set2 = new LinkedHashSet<String>();
            for(Row row: rs3) set2.add(row.getString("batter"));
            for(Row row: rs4) set2.add(row.getString("bowler"));

            squad2.addAll(set2);

            players.add(squad1);
            players.add(squad2);
        }

        return players;
    }
}
