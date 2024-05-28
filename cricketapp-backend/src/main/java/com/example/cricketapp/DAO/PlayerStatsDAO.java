package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.example.cricketapp.Model.PlayerBattingStats;
import com.example.cricketapp.Model.PlayerBowlingStats;
import com.example.cricketapp.config.CassandraConfigLoader;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

@Component
public class PlayerStatsDAO {
    public boolean isBatter(String name) {

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            String query = "select batter from ballrecord where batter=? LIMIT 1 ALLOW FILTERING;";
            PreparedStatement statement = session.prepare(query);
            ResultSet rs = session.execute(statement.bind(name));
            return rs.getAvailableWithoutFetching()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBowler(String name) {
        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            String query = "select bowler from ballrecord where bowler=? LIMIT 1 ALLOW FILTERING;";
            PreparedStatement statement = session.prepare(query);
            ResultSet rs = session.execute(statement.bind(name));
            return rs.getAvailableWithoutFetching()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public PlayerBattingStats getPlayerBattingStats(String name) {
        PlayerBattingStats player = new PlayerBattingStats();

        try (CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).withKeyspace(Keyspace.getKeyspaceName()).build()) {
            session.execute("drop table if exists batter_match_scores");
            session.execute("CREATE TABLE IF NOT EXISTS batter_match_scores ( batter text, match_no int, total_score int, PRIMARY KEY (batter, match_no) );");
            String query = "select batter, match_no, SUM(get_batter_run(outcome, score)) as total_score from batters_view where batter=? group by batter, match_no;";
            PreparedStatement statement = session.prepare(query);
            ResultSet rs = session.execute(statement.bind(name));

            BatchStatementBuilder batchBuilder = BatchStatement.builder(DefaultBatchType.LOGGED);
            for(Row row: rs) {
                String insertQuery = "INSERT INTO batter_match_scores (batter, match_no, total_score) values (?,?,?) ";

                SimpleStatement stmt = SimpleStatement.newInstance(insertQuery,
                        row.getString("batter"),
                        row.getInt("match_no"),
                        row.getInt("total_score")
                );
                batchBuilder.addStatement(stmt);
            }
            session.execute(batchBuilder.build());

            String query1 = "select batter, COUNT(match_no) as matches, SUM(total_score) as total_score, AVG(CAST(total_score As double)) as average, MAX(total_score) as hs from batter_match_scores group by batter ALLOW FILTERING;";
            ResultSet set = session.execute(query1);

            Row row = set.one();
            player.setBatterName(row.getString("batter"));
            player.setMatches(row.getLong("matches"));
            player.setRuns(row.getInt("total_score"));
            player.setAverage(Double.parseDouble(String.format("%.2f", row.getDouble("average"))));
            player.setHighestScore(row.getInt("hs"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return player;
    }

    public PlayerBowlingStats getPlayerBowlingStats(String name) {
        PlayerBowlingStats player = new PlayerBowlingStats();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            String query1 = "SELECT bowler, SUM(get_wickets(outcome)) as Wickets, SUM(get_runs_given(outcome, score)) as runs_conceded, COUNT(get_overs(outcome))/6 + CAST(COUNT(get_overs(outcome))%6/10.0 as double) as overs_bowled, SUM(get_runs_given(outcome, score))/(COUNT(get_overs(outcome))/6.0) as economy from bowlers_view where bowler=? group by match_no ALLOW FILTERING;";
            PreparedStatement statement1 = session.prepare(query1);
            ResultSet rs1 = session.execute(statement1.bind(name));

            String query2 = "SELECT bowler, SUM(get_wickets(outcome)) as Wickets, SUM(get_runs_given(outcome, score)) as runs_conceded, COUNT(get_overs(outcome))/6 + CAST(COUNT(get_overs(outcome))%6/10.0 as double) as overs_bowled, SUM(get_runs_given(outcome, score))/(COUNT(get_overs(outcome))/6.0) as economy from bowlers_view where bowler=? ALLOW FILTERING;";
            PreparedStatement statement2 = session.prepare(query2);
            ResultSet rs2 = session.execute(statement2.bind(name));

            Row rs = rs2.one();

            player.setBowlerName(rs.getString("bowler"));
            player.setMatches(rs1.getAvailableWithoutFetching());
            player.setTotalWickets(rs.getInt("Wickets"));
            player.setOvers(rs.getDouble("overs_bowled"));
            player.setRuns(rs.getInt("runs_conceded"));
            player.setAverageEconomy(Double.parseDouble(String.format("%.2f", rs.getDouble("economy"))));

        }
        return player;
    }
}
