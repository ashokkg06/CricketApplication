package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.Model.MatchComments;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentsDAO {
    public List<List<MatchComments>> getComments(int matchNo) {
        List<List<MatchComments>> commentsList = new ArrayList<>();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            for(int i=2; i>0; i--) {
                String query = "select overs, outcome, comment from ballrecord where match_no=? and inningno=? order by ballnumber desc;";

                PreparedStatement statement = session.prepare(query);

                ResultSet rs = session.execute(statement.bind(matchNo, i));

                List<MatchComments> commentList = new ArrayList<>();

                for(Row row: rs) {
                    MatchComments comments = new MatchComments();
                    comments.setOvers(row.getFloat("overs"));
                    comments.setOutcome(row.getString("outcome"));
                    comments.setComment(row.getString("comment"));
                    commentList.add(comments);
                }
                commentsList.add(commentList);
            }
        }

        return commentsList;
    }
}
