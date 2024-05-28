package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.BatterMatchStatsDAO;
import com.example.cricketapp.Model.BatterMatchStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BatterMatchService {
    @Autowired
    BatterMatchStatsDAO batterMatchStatsDAO;

    @Cacheable(value = "battermatchstats", key = "#season + '-' + #matchNo")
    public List<List<BatterMatchStats>> getBatterMatchStats(int matchNo, int season) {
        return batterMatchStatsDAO.getBatterMatchStats(matchNo);
    }
}
