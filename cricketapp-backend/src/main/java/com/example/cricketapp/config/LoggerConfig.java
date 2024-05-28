package com.example.cricketapp.config;

import com.example.cricketapp.CricketappApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerConfig {
    private static final Logger logger = LoggerFactory.getLogger(CricketappApplication.class);

    public static Logger getLogger() {
        return logger;
    }
}
