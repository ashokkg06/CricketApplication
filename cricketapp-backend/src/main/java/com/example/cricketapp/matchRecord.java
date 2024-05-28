package com.example.cricketapp;

public class matchRecord {
    public static String showDB = "SHOW DATABASES";
    public static String tbName;
    public static String createTable;
    public static String insertvalues;
    public static String updateValues;
    public static String deleteFromTable;
    public static String deleteDb;
    public static String readValues;
    public static String getNames;
    public static String getNameAndPass;

    public static void createQuery(String dbName, String tbName) {
        matchRecord.tbName = tbName;
        createTable = "CREATE TABLE " + tbName + " (season INT, date VARCHAR(255), match_number INT, match_type VARCHAR(10), venue VARCHAR(255), location VARCHAR(255), team1 VARCHAR(255), team2 VARCHAR(255), toss_won VARCHAR(255), toss_decision VARCHAR(255), umpire1 VARCHAR(255), umpire2 VARCHAR(255), reserve_umpire VARCHAR(255), match_referee VARCHAR(255), winner VARCHAR(255), winner_runs INT, winner_wickets INT, man_of_match VARCHAR(255))";
        insertvalues = "INSERT INTO " + tbName + " (season, date, match_number, match_type, venue, location, team1, team2, toss_won, toss_decision, umpire1, umpire2, reserve_umpire, match_referee, winner, winner_runs, winner_wickets, man_of_match) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        updateValues = "UPDATE " + tbName + "  SET ? = ? WHERE match_number = ?";
        deleteFromTable = "DELETE FROM " + tbName + "  WHERE match_number = ?";
        deleteDb = "DELETE DATABASE " + tbName;
        readValues = "SELECT * FROM " + tbName;

        //Operations
//        getNames = "SELECT Email FROM " +  dbName + "." + tbName;
//        getNameAndPass = "SELECT Email, Password FROM " +  dbName + "." + tbName;
    }

    public static String getTbName() {
        return tbName;
    }

    public static void setTbName(String tbName) {
        matchRecord.tbName = tbName;
    }

    public static String getCreateTable() {
        return createTable;
    }

    public static void setCreateTable(String createTable) {
        matchRecord.createTable = createTable;
    }

    public static String getInsertvalues() {
        return insertvalues;
    }

    public static void setInsertvalues(String insertvalues) {
        matchRecord.insertvalues = insertvalues;
    }

    public static String getUpdateValues() {
        return updateValues;
    }

    public static void setUpdateValues(String updateValues) {
        matchRecord.updateValues = updateValues;
    }

    public static String getDeleteFromTable() {
        return deleteFromTable;
    }

    public static void setDeleteFromTable(String deleteFromTable) {
        matchRecord.deleteFromTable = deleteFromTable;
    }

    public static String getDeleteDb() {
        return deleteDb;
    }

    public static void setDeleteDb(String deleteDb) {
        matchRecord.deleteDb = deleteDb;
    }

    public static String readAllValues() {
        return readValues;
    }

    public static void setReadValues(String readValues) {
        matchRecord.readValues = readValues;
    }

    public static String getGetNames() {
        return getNames;
    }

    public static void setGetNames(String getNames) {
        matchRecord.getNames = getNames;
    }

    public static String getGetNameAndPass() {
        return getNameAndPass;
    }

    public static void setGetNameAndPass(String getNameAndPass) {
        matchRecord.getNameAndPass = getNameAndPass;
    }

    public static String getShowDB() {
        return showDB;
    }

    public static void setShowDB(String showDB) {
        matchRecord.showDB = showDB;
    }


}

