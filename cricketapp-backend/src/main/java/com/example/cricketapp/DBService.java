package com.example.cricketapp;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.*;
import com.example.cricketapp.config.CassandraConfigLoader;
import com.example.cricketapp.keyspace.Keyspace;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBService {
    public static String selectedDbName = "";
    public void importData(String databaseName) throws SQLException {
        CRUD crud = new CRUD();
        crud.createDatabase(databaseName);
        crud.createTable("ballRecord");
        crud.createTable("matchRecord");
        crud.createTable("admin");

        ImportCSV importCSV = new ImportCSV();
        importCSV.addData("C:\\Users\\kgash\\OneDrive\\Desktop\\Java programs\\Zoho\\Incubation\\CricketApp\\cricketapp\\src\\main\\resources\\each_ball_records.csv", "ballRecord");
        importCSV.addData("C:\\Users\\kgash\\OneDrive\\Desktop\\Java programs\\Zoho\\Incubation\\CricketApp\\cricketapp\\src\\main\\resources\\each_match_records.csv", "matchRecord");

//        crud.createTable("batterStats");
//        crud.createTable("bowlerStats");
    }

    public void addDataSet(int season, String path1, String path2) {
        System.out.println(season);
        Keyspace.setKeyspaceName(season);
        System.out.println("Keyspace: set");
		createKeySpace(Keyspace.getKeyspaceName());
		createTables();
		importCSV(path1, path2);
		createMaterializedViews();
		createFunctions();
		insertValues();
    }

    public void createKeySpace(String keySpaceName) {
        try (CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).build()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + keySpaceName + " WITH replication = {'class': 'NetworkTopologyStrategy', 'replication_factor': 1};");
            System.out.println("Keyspace: done");
        }
    }

    public void createTables() {
        try (CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).withKeyspace(Keyspace.getKeyspaceName()).build()) {
            session.execute("CREATE TABLE IF NOT EXISTS ballrecord (" +
                    "match_no int, ballnumber int, inningno int, overs float, outcome text, " +
                    "batter text, bowler text, comment text, score int, " +
                    "PRIMARY KEY (match_no, inningno, ballnumber, batter, bowler))");
            session.execute("CREATE TABLE IF NOT EXISTS matchrecord (" +
                    "season int, date text, match_number int PRIMARY KEY, match_type text, " +
                    "venue text, location text, team1 text, team2 text, toss_won text, " +
                    "toss_decision text, umpire1 text, umpire2 text, reserve_umpire text, " +
                    "match_referee text, winner text, winner_runs int, winner_wickets int, man_of_match text)");

            session.execute("CREATE TABLE IF NOT EXISTS teamrecord (match_number int, team1 text, team2 text, winner text, team1score int, team2score int, team1overs double, team2overs double, team1wickets int, team2wickets int, PRIMARY KEY(match_number))");

            System.out.println("Tables: done");
        }
    }

    public void importCSV(String path1, String path2) {
        try (CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).build()) {
//			session.execute("COPY cricketapp.ballrecord (match_no, ballnumber, inningno, overs, outcome, batter, bowler, comment, score) FROM 'each_ball_records.csv' WITH DELIMITER=',' AND HEADER=TRUE;");
//			session.execute("COPY matchrecord (season, date, match_number, match_type, venue, location, team1, team2, toss_won, toss_decision, umpire1, umpire2, reserve_umpire, match_referee, winner, winner_runs, winner_wickets, man_of_match) FROM '/home/each_match_records.csv' WITH DELIMITER=',' AND HEADER=TRUE;");

            boolean isBallRecord = session.execute("select * from ballrecord limit 1").getAvailableWithoutFetching() > 0;
            boolean isMatchRecord = session.execute("select * from matchrecord limit 1").getAvailableWithoutFetching() > 0;

            if(!isBallRecord) {
                System.out.println("importing ballrecord csv...");
//                loadCSVData(session, "ballRecord", absolutePath1, "INSERT INTO cricketapp.ballrecord (match_no, ballnumber, inningno, overs, outcome, batter, bowler, comment, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String insertQuery = "INSERT INTO ballrecord (match_no, ballnumber, inningno, overs, outcome, batter, bowler, comment, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ArrayNode jsonArray = convertCSVToJson(path1);
                    loadJsonData(session, "ballRecord", jsonArray, insertQuery);
            }
            else {
                System.out.println("ballrecord data already exists!");
            }
            if(!isMatchRecord) {
                System.out.println("importing matchrecord csv...");
//                loadCSVData(session, "matchRecord", absolutePath2, "INSERT INTO cricketapp.matchrecord (season, date, match_number, match_type, venue, location, team1, team2, toss_won, toss_decision, umpire1, umpire2, reserve_umpire, match_referee, winner, winner_runs, winner_wickets, man_of_match) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String insertQuery = "INSERT INTO matchrecord (season, date, match_number, match_type, venue, location, team1, team2, toss_won, toss_decision, umpire1, umpire2, reserve_umpire, match_referee, winner, winner_runs, winner_wickets, man_of_match) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ArrayNode jsonArray = convertCSVToJson(path2);
                loadJsonData(session, "matchRecord", jsonArray, insertQuery);
            }
            else {
                System.out.println("matchrecord data already exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayNode convertCSVToJson(String filePath) throws IOException {
        ArrayNode jsonArray = new ObjectMapper().createArrayNode();

        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
                csvRecord.toMap().forEach(jsonObject::put);
                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }

    public static void loadJsonData(CqlSession session, String tableName, ArrayNode jsonArray, String insertQuery) {
        try {
//            PreparedStatement preparedStatement = session.prepare(insertQuery);

            BatchStatementBuilder batchBuilder = BatchStatement.builder(DefaultBatchType.LOGGED);

            for (JsonNode jsonNode : jsonArray) {
                SimpleStatement stmt;
                if (tableName.equals("ballRecord")) {
                    stmt = SimpleStatement.newInstance(insertQuery,
                            jsonNode.get("match_no").asInt(),
                            jsonNode.get("ballnumber").asInt(),
                            jsonNode.get("inningno").asInt(),
                            jsonNode.get("over").floatValue(),
                            jsonNode.get("outcome").asText(),
                            jsonNode.get("batter").asText(),
                            jsonNode.get("bowler").asText(),
                            jsonNode.get("comment").asText(),
                            jsonNode.get("score").asInt()
                    );
                } else {
                    stmt = SimpleStatement.newInstance(insertQuery,
                            jsonNode.get("season").asInt(),
                            jsonNode.get("date").asText(),
                            jsonNode.get("match_number").asInt(),
                            jsonNode.get("match_type").asText(),
                            jsonNode.get("venue").asText(),
                            jsonNode.get("location").asText(),
                            jsonNode.get("team1").asText(),
                            jsonNode.get("team2").asText(),
                            jsonNode.get("toss_won").asText(),
                            jsonNode.get("toss_decision").asText(),
                            jsonNode.get("umpire1").asText(),
                            jsonNode.get("umpire2").asText(),
                            jsonNode.get("reserve_umpire").asText(),
                            jsonNode.get("match_referee").asText(),
                            jsonNode.get("winner").asText(),
                            getIntOrNull(jsonNode.get("winner_runs")),
                            getIntOrNull(jsonNode.get("winner_wickets")),
                            jsonNode.get("man_of_match").asText()
                    );
                }
                batchBuilder.addStatement(stmt);
                if(batchBuilder.getStatementsCount() >= 100) {
                    session.execute(batchBuilder.build());
                    batchBuilder.clearStatements();
                }
            }
            if (batchBuilder.getStatementsCount() > 0) {
                session.execute(batchBuilder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void loadCSVData(CqlSession session, String tableName,String filePath, String insertQuery) throws IOException {
        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            PreparedStatement preparedStatement = session.prepare(insertQuery);

            if(tableName.equals("ballRecord")) {
                for (CSVRecord csvRecord : csvParser) {
                    session.execute(preparedStatement.bind(
                            Integer.parseInt(csvRecord.get("match_no")),
                            Integer.parseInt(csvRecord.get("ballnumber")),
                            Integer.parseInt(csvRecord.get("inningno")),
                            Float.parseFloat(csvRecord.get("over")),
                            csvRecord.get("outcome"),
                            csvRecord.get("batter"),
                            csvRecord.get("bowler"),
                            csvRecord.get("comment"),
                            Integer.parseInt(csvRecord.get("score"))
                    ));
                }
            }
            else {
                for(CSVRecord csvRecord: csvParser) {
                    session.execute(preparedStatement.bind(
                            Integer.parseInt(csvRecord.get("season")),
                            csvRecord.get("date"),
                            Integer.parseInt(csvRecord.get("match_number")),
                            csvRecord.get("match_type"),
                            csvRecord.get("venue"),
                            csvRecord.get("location"),
                            csvRecord.get("team1"),
                            csvRecord.get("team2"),
                            csvRecord.get("toss_won"),
                            csvRecord.get("toss_decision"),
                            csvRecord.get("umpire1"),
                            csvRecord.get("umpire2"),
                            csvRecord.get("reserve_umpire"),
                            csvRecord.get("match_referee"),
                            csvRecord.get("winner"),
                            getIntOrNull(csvRecord.get("winner_runs")),
                            getIntOrNull(csvRecord.get("winner_wickets")),
                            csvRecord.get("man_of_match")
                    ));
                }
            }
        }
    }

    private static Integer getIntOrNull(JsonNode node) {
        return node.isNull() ? null : node.asInt();
    }
    private static Integer getIntOrNull(String value) {
        return value==null || value.isEmpty()? null : Integer.parseInt(value);
    }

    public void insertValues() {
        try (CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).build()) {
            ResultSet set = session.execute("select match_number from teamrecord where match_number=1 ALLOW FILTERING;");
            if (set.getAvailableWithoutFetching() == 0) {
                for (int index = 1; index <= 75; index += 15) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("(");
                    for (int j = index; j <= index + 14 && j <= 75; j++) {
                        sb.append(j).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(")");
                    String range = sb.toString();
                    System.out.println(range);
                    ResultSet rs1 = session.execute(" select match_number, team1, team2, winner from matchrecord where match_number IN " + range);
                    ResultSet rs2 = session.execute("SELECT match_no, SUM(get_score_1(score, inningno)) as teamscore1, SUM(get_score_2(score, inningno)) as teamscore2, SUM(get_wickets_1(outcome, inningno)) as teamwickets1,  SUM(get_wickets_2(outcome, inningno)) as teamwickets2 , COUNT(get_overs_1(outcome, inningno))/6 + CAST(COUNT(get_overs_1(outcome, inningno))%6/10.0 as double) as teamover1, COUNT(get_overs_2(outcome, inningno))/6 + CAST(COUNT(get_overs_2(outcome, inningno))%6/10.0 as double) as teamover2 from cricketapp.ballrecord where match_no IN " + range + " group by match_no ALLOW FILTERING;");

                    List<Row> row1 = rs1.all();
                    List<Row> row2 = rs2.all();

                    BatchStatementBuilder batchBuilder = BatchStatement.builder(DefaultBatchType.LOGGED);
                    for (int i = 0; i < row1.size(); i++) {
                        String insertQuery = "INSERT INTO " +
                                "teamrecord (match_number, team1, team2, winner, team1score, team2score, team1overs, team2overs, team1wickets, team2wickets)" +
                                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        Row result1 = row1.get(i);
                        Row result2 = row2.get(i);

                        SimpleStatement stmt = SimpleStatement.newInstance(insertQuery,
                                result1.getInt("match_number"),
                                result1.getString("team1"),
                                result1.getString("team2"),
                                result1.getString("winner"),
                                result2.getInt("teamscore1"),
                                result2.getInt("teamscore2"),
                                result2.getDouble("teamover1"),
                                result2.getDouble("teamover2"),
                                result2.getInt("teamwickets1"),
                                result2.getInt("teamwickets2")
                        );
                        batchBuilder.addStatement(stmt);
                    }
                    session.execute(batchBuilder.build());
                }
                System.out.println("Tables values are inserted");
            } else {
                System.out.println("teamrecord Data exists, hence skipped inserting values");
            }
        }
    }
    public void createMaterializedViews() {
        try (CqlSession session = CqlSession.builder().withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).withConfigLoader(CassandraConfigLoader.getConfigLoader()).build()) {
            session.execute("CREATE MATERIALIZED VIEW IF NOT EXISTS batters_view AS SELECT * FROM ballrecord WHERE match_no IS NOT NULL AND inningno IS NOT NULL AND batter IS NOT NULL AND ballnumber IS NOT NULL AND bowler IS NOT NULL PRIMARY KEY ((batter), match_no, inningno, ballnumber, bowler);");
            session.execute("CREATE MATERIALIZED VIEW IF NOT EXISTS bowlers_view AS SELECT * FROM ballrecord WHERE match_no IS NOT NULL AND inningno IS NOT NULL AND bowler IS NOT NULL AND ballnumber IS NOT NULL AND batter IS NOT NULL PRIMARY KEY ((bowler), match_no, inningno, ballnumber, batter);");
            session.execute("CREATE MATERIALIZED VIEW IF NOT EXISTS pointstable_view AS SELECT * FROM matchrecord WHERE winner IS NOT NULL AND match_number IS NOT NULL PRIMARY KEY (winner, match_number);");
            session.execute("CREATE MATERIALIZED VIEW IF NOT EXISTS teamrecord_view_1 As SELECT * FROM teamrecord WHERE team1 IS NOT NULL AND match_number IS NOT NULL PRIMARY KEY(team1, match_number);");
            session.execute("CREATE MATERIALIZED VIEW IF NOT EXISTS teamrecord_view_2 As SELECT * FROM teamrecord WHERE team2 IS NOT NULL AND match_number IS NOT NULL PRIMARY KEY(team2, match_number);");
            System.out.println("Materialized views created");
        }
    }

    public void createFunctions() {
        try(CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).build()) {
            session.execute("CREATE FUNCTION IF NOT EXISTS get_wickets(outcome text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"w\")) { return 1; } else {return 0;}';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_overs(outcome text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(!(outcome.endsWith(\"nb\") || outcome.endsWith(\"wd\"))) { return 1; } else return null;';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_score_1(score int, inningno int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(inningno==1) { return score; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_score_2(score int, inningno int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(inningno==2) { return score; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_wickets_1(outcome text, inningno int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"w\") && inningno==1) { return 1; } else {return 0;}';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_wickets_2(outcome text, inningno int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"w\") && inningno==2) { return 1; } else {return 0;}';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_overs_1(outcome text, inningno int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(!(outcome.endsWith(\"nb\") || outcome.endsWith(\"wd\")) && inningno==1) { return 1; } else return null;';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_overs_2(outcome text, inningno int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(!(outcome.endsWith(\"nb\") || outcome.endsWith(\"wd\")) && inningno==2) { return 1; } else return null;';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_batter_run(outcome text, score int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java AS 'if (outcome.endsWith(\"nb\") || outcome.endsWith(\"wd\")) { return score - 1; } else if (outcome.endsWith(\"lb\") || outcome.endsWith(\"b\")) { return 0; } else { return score; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS isBall(outcome text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java AS 'if (outcome.endsWith(\"wd\")) { return null; } else { return 1; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getFoursCount(outcome text, score int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java AS 'if (outcome.equals(\"4\") || (score==5 && outcome.endsWith(\"nb\"))) { return 1; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getSixesCount(score int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java AS 'if (score==6 || score==7) { return 1; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getDismissalInfo(comment text) RETURNS NULL ON NULL INPUT RETURNS text LANGUAGE java As 'if(comment.contains(\"Wicket\")) { return comment.substring(comment.indexOf(\"-\")+2, comment.indexOf(\"batsmen\")-1); } else { return \"not out\"; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_runs_given(outcome text, score int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"lb\") && score>3) { return 0; } else if(outcome.endsWith(\"lb\") || (outcome.endsWith(\"b\") && !outcome.endsWith(\"nb\"))) { return score-1; } else { return score; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_noball(outcome text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"nb\")) { return 1; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_legbye(outcome text, score int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"lb\")) { return score; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_wides(outcome text, score int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"wd\")) { return score; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_byes(outcome text, score int) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(outcome.endsWith(\"b\") && !outcome.endsWith(\"lb\") && !outcome.endsWith(\"nb\")) { return score; } else { return null; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getteamtotalscore(team1score int, team2score int, team1 text, team2 text, team text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(team1.equals(team)) { return team1score; } else if(team2.equals(team)) { return team2score; } else { return 0; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getopteamtotalscore(team1score int, team2score int, team1 text, team2 text, team text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(team1.equals(team)) { return team2score; } else if(team2.equals(team)) { return team1score; } else { return 0; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getteamtotalovers(team1overs double, team2overs double, team1 text, team2 text, team text) RETURNS NULL ON NULL INPUT RETURNS double LANGUAGE java As 'if(team1.equals(team)) { return team1overs; } else if(team2.equals(team)) { return team2overs; } else { return 0.0; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getopteamtotalovers(team1overs double, team2overs double, team1 text, team2 text, team text) RETURNS NULL ON NULL INPUT RETURNS double LANGUAGE java As 'if(team1.equals(team)) { return team2overs; } else if(team2.equals(team)) { return team1overs; } else { return 0.0; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getteamtotalwickets(team1wickets int, team2wickets int, team1 text, team2 text, team text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(team1.equals(team)) { return team1wickets; } else if(team2.equals(team)) { return team2wickets; } else { return 0; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS getopteamtotalwickets(team1wickets int, team2wickets int, team1 text, team2 text, team text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(team1.equals(team)) { return team2wickets; } else if(team2.equals(team)) { return team1wickets; } else { return 0; }';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_winner(winner text, team text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(winner.equals(team)) { return 1; } else return null;';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_lose(winner text, team text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(!winner.equals(team) && !winner.equals(\"No Result\")) { return 1; } else return null;';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_noresult(winner text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(winner.equals(\"No Result\")) { return 1; } else return null;';");
            session.execute("CREATE FUNCTION IF NOT EXISTS get_tie(winner text) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java As 'if(winner.equals(\"tie\")) { return 1; } else return null;';");
            System.out.println("Functions created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getCsvFilenames() {
        try(CqlSession session = CqlSession.builder().withConfigLoader(CassandraConfigLoader.getConfigLoader()).withKeyspace(CqlIdentifier.fromCql(Keyspace.getKeyspaceName())).build()) {
            String query = "desc keyspaces";
            ResultSet rs = session.execute(query);
            List<String> list = new ArrayList<>();
            for(Row row: rs) {
                String s = row.getString(2);
                if(s!=null && s.startsWith("ipl")) {
                    list.add(s.replace("ipl",""));
                }
            }
            return list;
        }
    }
}
