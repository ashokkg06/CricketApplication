package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.Model.ExtrasData;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExtrasDAO {

    public List<ExtrasData> getExtrasData(int matchNo) {
        List<ExtrasData> extrasDataList = new ArrayList<>();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            for(int i=1; i<=2; i++) {
                String query = "select match_no, inningno, SUM(get_wides(outcome,score)) as wides, SUM(get_legbye(outcome,score)) as legbye, COUNT(get_noball(outcome)) as noball, SUM(get_byes(outcome, score)) as byes from ballrecord where match_no=? and inningno=? group by match_no, inningno;";
                PreparedStatement statement = session.prepare(query);
                ResultSet rs = session.execute(statement.bind(matchNo, i));

                for(Row row: rs) {
                    ExtrasData extrasData = new ExtrasData();
                    extrasData.setMatchNumber(row.getInt("match_no"));
                    extrasData.setInningNumber(row.getInt("inningno"));
                    extrasData.setNoBalls(row.getLong("noball"));
                    extrasData.setLegByes(row.getInt("legbye"));
                    extrasData.setWides(row.getInt("wides"));
                    extrasData.setByes(row.getInt("byes"));

                    extrasDataList.add(extrasData);
                }
            }
        }
        return extrasDataList;
    }
}
