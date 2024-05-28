package com.example.cricketapp;

public class ballRecord {
    public static String showDB = "SHOW DATABASES";
    public static String tableName;
    public static String createTable;
    public static String insertvalues;
    public static String updateValues;
    public static String deleteFromTable;
    public static String deleteDb;
    public static String readValues;
    public static String getNames;
    public static String getNameAndPass;

    public static void createQuery(String dbName, String tbName) {
        tableName = tbName;
        createTable = "CREATE TABLE " + tbName + " (match_no INT, ballnumber INT, inningno INT, overs FLOAT, outcome varchar(255), batter varchar(255), bowler varchar(255), comment varchar(255), score INT);";
        insertvalues = "INSERT INTO " + tbName + " (match_no, ballnumber, inningno, overs, outcome, batter, bowler, comment, score) VALUES (?,?,?,?,?,?,?,?,?)";
        updateValues = "UPDATE " + tbName + "  SET ? = ? WHERE over = ?";
        deleteFromTable = "DELETE FROM " + tbName + "  WHERE over = ?";
        deleteDb = "DELETE DATABASE " + tbName;
        readValues = "SELECT * FROM " + tbName;

        //Operations
//        getNames = "SELECT Email FROM " +  dbName + "." + tbName;
//        getNameAndPass = "SELECT Email, Password FROM " +  dbName + "." + tbName;
    }

    public static String getTableName() {
        return tableName;
    }

    public static void setTableName(String tbName) {
        ballRecord.tableName = tbName;
    }

    public static String getCreateTable() {
        return createTable;
    }

    public static void setCreateTable(String createTable) {
        ballRecord.createTable = createTable;
    }

    public static String getInsertvalues() {
        return insertvalues;
    }

    public static void setInsertvalues(String insertvalues) {
        ballRecord.insertvalues = insertvalues;
    }

    public static String getUpdateValues() {
        return updateValues;
    }

    public static void setUpdateValues(String updateValues) {
        ballRecord.updateValues = updateValues;
    }

    public static String getDeleteFromTable() {
        return deleteFromTable;
    }

    public static void setDeleteFromTable(String deleteFromTable) {
        ballRecord.deleteFromTable = deleteFromTable;
    }

    public static String getDeleteDb() {
        return deleteDb;
    }

    public static void setDeleteDb(String deleteDb) {
        ballRecord.deleteDb = deleteDb;
    }

    public static String readAllValues() {
        return readValues;
    }

    public static void setReadValues(String readValues) {
        ballRecord.readValues = readValues;
    }

    public static String getGetNames() {
        return getNames;
    }

    public static void setGetNames(String getNames) {
        ballRecord.getNames = getNames;
    }

    public static String getGetNameAndPass() {
        return getNameAndPass;
    }

    public static void setGetNameAndPass(String getNameAndPass) {
        ballRecord.getNameAndPass = getNameAndPass;
    }

    public static String getShowDB() {
        return showDB;
    }

    public static void setShowDB(String showDB) {
        ballRecord.showDB = showDB;
    }


}

