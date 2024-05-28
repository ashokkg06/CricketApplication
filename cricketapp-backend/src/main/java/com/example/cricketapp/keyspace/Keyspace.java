package com.example.cricketapp.keyspace;

public class Keyspace {

    public static String keyspaceName = "cricketapp";

    public static String getKeyspaceName() {
        return keyspaceName;
    }

//    public static void setSeason23() {
//        keyspaceName = "twentythree";
//    }
//
//    public static void setSeason22() {
//        keyspaceName = "twentytwo";
//    }

    public static void setKeyspaceName(int season) {
        keyspaceName = "ipl" + season;
    }
}
