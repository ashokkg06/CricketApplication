package com.example.cricketapp;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

public class DBConnection {
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String name = System.getenv("MYSQL_DB_USER");
    private static String pass = System.getenv("MYSQL_DB_PASSWORD");

    public static Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(url + "?" + "allowLoadLocalInfile=true", name, pass);
        } catch (SQLException ex) {
            return DriverManager.getConnection(url, name, pass);
        }
    }

    // public static Connection connect(String databaseName) throws Exception {
    //     return DriverManager.getConnection(url.replace(url.split("/")[3], ""), name, pass);
    // }

    public static class databaseNotFoundException extends SQLSyntaxErrorException {

        public databaseNotFoundException() {
            super("No database Found");
        }
    }
}
