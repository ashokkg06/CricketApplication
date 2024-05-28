package com.example.cricketapp;

public class adminquery {
    public static String showDB = "SHOW DATABASES";

    public static String tbName;
    public static String createTable;
    public static String insertvalues;
    public static String updateValues;
    public static String deleteFromTable;
    public static String deleteDb;
    public static String readValues;

    public static String admins;

    public static String adminByEmail;

    public static String adminById;

    public static void createQuery(String dbName, String tbName) {
        matchRecord.tbName = tbName;
        createTable = "CREATE TABLE " + tbName + " (id int NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), password VARCHAR(255), email VARCHAR(255))";
        insertvalues = "INSERT INTO " + tbName + " (name, email, password) VALUES (?,?,?)";
        updateValues = "UPDATE " + tbName + "  SET ? = ? WHERE name = ?";
        deleteFromTable = "DELETE FROM " + tbName + "  WHERE name = ?";
        deleteDb = "DELETE DATABASE " + tbName;
        readValues = "SELECT * FROM " + tbName;

//        Operations
        admins = "SELECT * FROM " +  dbName + "." + tbName;
        adminByEmail = String.format("SELECT * FROM %s.%s where email = ?", dbName, tbName);
        adminById = String.format("SELECT * FROM %s.%s where id = ?", dbName, tbName);
//        getNameAndPass = "SELECT email, Password FROM " +  dbName + "." + tbName + " where id = ?";
    }

    public static String getShowDB() {
        return showDB;
    }

    public static String getTbName() {
        return tbName;
    }

    public static String getCreateTable() {
        return createTable;
    }

    public static String getInsertvalues() {
        return insertvalues;
    }

    public static String getUpdateValues() {
        return updateValues;
    }

    public static String getDeleteFromTable() {
        return deleteFromTable;
    }

    public static String getDeleteDb() {
        return deleteDb;
    }

    public static String getReadValues() {
        return readValues;
    }

    public static String getAdmins() {
        return admins;
    }

    public static String getAdminByEmail() {
        return adminByEmail;
    }

    public static String getAdminById() {
        return adminById;
    }
}
