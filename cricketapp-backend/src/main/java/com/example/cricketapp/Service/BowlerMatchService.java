package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.BowlerMatchStatsDAO;
import com.example.cricketapp.Model.BowlerMatchStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BowlerMatchService {

    @Autowired
    BowlerMatchStatsDAO bowlerMatchStatsDAO;

    @Cacheable(value = "bowlermatchstats", key = "#season + '-' + #matchNo")
    public List<List<BowlerMatchStats>> getBowlerMatchStats(int matchNo, int season) {
        return bowlerMatchStatsDAO.getBowlerMatchStats(matchNo);
    }
}
