package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.Model.MatchData;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

@Component
public class MatchDetailsDAO {

    public MatchData getMatchDetails(int matchNo) {
        MatchData matchData = new MatchData();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            String query = "select * from matchrecord where match_number=?;";

            PreparedStatement statement = session.prepare(query);

            ResultSet result = session.execute(statement.bind(matchNo));

            for(Row rs: result) {
               matchData.setSeason(rs.getInt("season"));
               matchData.setDate(rs.getString("date"));
               matchData.setMatchNumber(rs.getInt("match_number"));
               matchData.setStadium(rs.getString("venue"));
               matchData.setLocation(rs.getString("location"));
               matchData.setTeam1(rs.getString("team1"));
               matchData.setTeam2(rs.getString("team2"));
               matchData.setTossWinner(rs.getString("toss_won"));
               matchData.setTossDecision(rs.getString("toss_decision"));
               matchData.setUmpire1(rs.getString("umpire1"));
               matchData.setUmpire2(rs.getString("umpire2"));
               matchData.setReserveUmpire(rs.getString("reserve_umpire"));
               matchData.setRefree(rs.getString("match_referee"));
               matchData.setWinner(rs.getString("winner"));
               matchData.setManOfMatch(rs.getString("man_of_match"));
            }
        }

        return matchData;
    }
}
