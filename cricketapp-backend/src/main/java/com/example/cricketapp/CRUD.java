package com.example.cricketapp;

import java.sql.*;
import java.util.*;

public class CRUD {
    public boolean isDatabaseExists(String dbname) {
        try (Connection cn = DBConnection.connect()) {
            Statement stm = cn.createStatement();
            ResultSet result = stm.executeQuery("SHOW DATABASES");
            while (result.next()) {
                String dbNames = result.getString("Database");
                if (dbNames.equals(dbname.toLowerCase()))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTableExists(String tbName) {
        try (Connection cn = DBConnection.connect()) {
            Statement stm = cn.createStatement();
            ResultSet result = stm.executeQuery("SHOW TABLES FROM " + DB.getDbName());
            while (result.next()) {
                String tbNames = result.getString("Tables_in_" + DB.getDbName());
                if (tbNames.equals(tbName.toLowerCase()))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createDatabase(String databaseName) {
        if (!isDatabaseExists(databaseName)) {
            try {
                Connection cn = DBConnection.connect();
                Statement stm = cn.createStatement();
                String query = "CREATE DATABASE " + databaseName;
                stm.execute(query);
                System.out.println("DB Action: " + databaseName + " database created successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DB.setDbName(databaseName);
    }

    public void createTable(String tableName) {
        if (!isTableExists(tableName)) {
            try {
                Connection cn = DBConnection.connect();
                Statement stm = cn.createStatement();
                stm.execute("use cricketapp");
                String query = "";
                if (tableName.equals("ballRecord")) {
                    ballRecord.createQuery(DB.getDbName(), tableName);
                    query = ballRecord.getCreateTable();
                } else if (tableName.equals("matchRecord")) {
                    matchRecord.createQuery(DB.getDbName(), tableName);
                    query = matchRecord.getCreateTable();
                } else {
                    adminquery.createQuery(DB.getDbName(), tableName);
                    query = adminquery.getCreateTable();
                }
                System.out.println("QUERY: " + query);
                stm.execute(query);
                System.out.println("DB Action: " + tableName + " table created successfully");
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(tableName.equals("admin")) {
            adminquery.createQuery(DB.getDbName(), tableName);
            return;
        }
        else {
            try {
                Connection cn = DBConnection.connect();
                Statement stm = cn.createStatement();
                stm.execute("use cricketapp");
                String query = "drop table " + tableName;
                stm.execute(query);
                createTable(tableName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getTableValues(String tableName) {
        try (Connection cn = DBConnection.connect()) {
            List<Object[]> table1Data = new ArrayList<>();
            List<Object[]> table2Data = new ArrayList<>();
            Statement stm = cn.createStatement();
            ResultSet rs1 = stm.executeQuery(ballRecord.readAllValues());
            ResultSet rs2 = stm.executeQuery(matchRecord.readAllValues());
            while (rs1.next()) {
                Object[] row = {rs1.getInt(1), rs1.getInt(2), rs1.getInt(3), rs1.getFloat(4), rs1.getString(5), rs1.getString(6), rs1.getString(7), rs1.getString(8), rs1.getInt(9)};
                table1Data.add(row);
            }
            while (rs2.next()) {
                Object[] row = {rs1.getInt(1), rs1.getString(2), rs1.getInt(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7), rs1.getString(8), rs1.getString(9), rs1.getString(10), rs1.getString(11), rs1.getString(12), rs1.getString(13), rs1.getString(14), rs1.getString(15), rs1.getInt(16), rs1.getInt(17), rs1.getString(18)};
                table2Data.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getMatchStatistics() {
        try (Connection cn = DBConnection.connect();
             Statement stm = cn.createStatement()) {
            String query = "SELECT MAX(m.date) AS date, " +
                    "MAX(m.season) AS season, " +
                    "m.match_number AS matchNumber, " +
                    "MAX(m.venue) AS venue, " +
                    "MAX(m.location) AS location, " +
                    "MAX(m.team1) AS team1, " +
                    "MAX(m.team2) AS team2, " +
                    "MAX(m.winner) AS winner, " +
                    "MAX(CASE WHEN inningno = 1 THEN Team1_Score END) AS team1Score, " +
                    "MAX(CASE WHEN inningno = 2 THEN Team2_Score END) AS team2Score, " +
                    "MAX(CASE WHEN inningno = 1 THEN Team1_Wickets END) AS team1Wickets, " +
                    "MAX(CASE WHEN inningno = 2 THEN Team2_Wickets END) AS team2Wickets, " +
                    "SUM(CASE WHEN inningno = 2 THEN Overs END) AS overs, " +
                    "MAX(m.winner_runs) AS winnerRuns, " +
                    "MAX(m.winner_wickets) AS winnerWickets " +
                    "FROM ( " +
                    "SELECT match_no, " +
                    "inningno, " +
                    "SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) AS Team1_Score, " +
                    "SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) AS Team2_Score, " +
                    "SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) AS Team1_Wickets, " +
                    "SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) AS Team2_Wickets, " +
                    "FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10) AS Overs " +
                    "FROM ballrecord " +
                    "GROUP BY match_no, inningno " +
                    ") AS match_stats " +
                    "LEFT JOIN matchrecord AS m ON m.match_number = match_stats.match_no " +
                    "GROUP BY m.match_number";
            return stm.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void InsertMatchesDataValues() {
        try (Connection cn = DBConnection.connect();
             Statement stm = cn.createStatement()) {
            String query = "SELECT " +
                    "    MAX(m.date) as date, " +
                    "    MAX(m.season) as season, " +
                    "    m.match_number, " +
                    "    MAX(m.venue) as venue, " +
                    "    MAX(m.location) as location, " +
                    "    MAX(m.team1) as team1, " +
                    "    MAX(m.team2) as team2, " +
                    "    MAX(m.winner) as winner, " +
                    "    MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score, " +
                    "    MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score, " +
                    "    MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets, " +
                    "    MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets, " +
                    "    MAX(CASE WHEN inningno = 2 THEN Overss END) as Overs, " +
                    "    MAX(m.winner_runs) as winner_runs, " +
                    "    MAX(m.winner_wickets) as winner_wickets " +
                    "FROM ( " +
                    "    SELECT " +
                    "        match_no, " +
                    "        inningno, " +
                    "        SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score, " +
                    "        SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score, " +
                    "        SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets, " +
                    "        SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets, " +
                    "        ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as Overss " +
                    "    FROM " +
                    "        ballrecord " +
                    "    GROUP BY " +
                    "        match_no, inningno " +
                    ") AS match_stats " +
                    "LEFT JOIN " +
                    "    matchrecord AS m ON m.match_number = match_stats.match_no " +
                    "GROUP BY " +
                    "    m.match_number;";
            ResultSet rs = stm.executeQuery(query);
            System.out.println(rs.getFetchSize());
            System.out.println("Matches data query executed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}