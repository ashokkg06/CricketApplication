package com.example.cricketapp.DAO;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.Model.PointsTableData;
import com.example.cricketapp.keyspace.Keyspace;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PointsTableDAO {

    public List<PointsTableData> getPointsTable() {
        List<PointsTableData> list = new ArrayList<>();

        try (CqlSession session = CqlSession.builder().withKeyspace(Keyspace.getKeyspaceName()).build()) {
            String query = "select winner, COUNT(winner) As wins, 14 - COUNT(winner) as loses, COUNT(winner)*2 as PTS from pointstable_view where match_number<71 group by winner ALLOW FILTERING;";
            ResultSet rs = session.execute(query);
            System.out.println("QUERY EXECUTED: " + query);

            String query1 = "select team1, SUM(team1score) as teamscore, SUM(team2score) as opteamscore, SUM(team1overs) as teamovers, SUM(team2overs) as opteamovers from teamrecord_view_1 where match_number<70 group by team1 ALLOW FILTERING;";
            String query2 = "select team2, SUM(team2score) as teamscore, SUM(team1score) as opteamscore, SUM(team2overs) as teamovers, SUM(team1overs) as opteamovers from teamrecord_view_2 where match_number<70 group by team2 ALLOW FILTERING;";

            ResultSet rs1 = session.execute(query1);
            ResultSet rs2 = session.execute(query2);

            List<Row> row1 = rs1.all();
            List<Row> row2 = rs2.all();

            int index=0;
            for (Row row: rs) {
                PointsTableData pointsTableData = new PointsTableData();
                if(!row.getString("winner").equals("No Result")) {
                    Row r1 = row1.get(index);
                    Row r2 = row2.get(index);

                    pointsTableData.setTeam(row.getString("winner"));
                    pointsTableData.setWins(row.getLong("wins"));
                    pointsTableData.setLose(row.getLong("loses"));
                    pointsTableData.setPoints(row.getLong("pts"));
                    pointsTableData.setNetRunRate(Double.parseDouble(String.format("%.3f",getNetRunRate(r1.getInt("teamscore"), r2.getInt("teamscore"), r1.getInt("opteamscore"), r2.getInt("opteamscore"), r1.getDouble("teamovers"), r2.getDouble("teamovers"), r1.getDouble("opteamovers"), r2.getDouble("opteamovers")))));
                    list.add(pointsTableData);
                    index++;
                }
            }
        }
        return list;
    }

    public Double getNetRunRate(int teamscore1, int teamscore2, int opteamscore1, int opteamscore2, Double teamovers1, Double teamovers2, Double opteamovers1, Double opteamovers2) {
        Double netRunRate = null;

        int score = teamscore1 + teamscore2;
        int opscore = opteamscore1 + opteamscore2;
        Double teamovers = teamovers1 + teamovers2;
        Double opteamovers = opteamovers1 + opteamovers2;
        netRunRate = (score/teamovers) - (opscore/opteamovers);

        return netRunRate;
    }
}
