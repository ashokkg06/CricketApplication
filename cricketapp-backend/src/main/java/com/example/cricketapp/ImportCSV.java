package com.example.cricketapp;

import java.sql.Connection;
import java.sql.Statement;

public class ImportCSV {

    public void addData(String path, String tableName) {
        try {
            Connection cn = DBConnection.connect();
            Statement stm = cn.createStatement();
            stm.execute("use cricketapp");
            String query = "LOAD DATA LOCAL INFILE '" + path.replace("\\", "/") + "' " +
                    "INTO TABLE " + tableName +
                    " FIELDS TERMINATED BY ',' " +
                    "OPTIONALLY ENCLOSED BY '\"' " +
                    "LINES TERMINATED BY '\\r\\n' " +
                    "IGNORE 1 ROWS;";
            System.out.println("QUERY: " + query);
            stm.execute(query.toLowerCase());
            System.out.println("DB Action: " + " csv file imported successfully");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
