package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.MatchStatisticsDAO;
import com.example.cricketapp.Model.MatchStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MatchStatisticsService {
    @Autowired
    private MatchStatisticsDAO matchStatisticsDAO;

    @Cacheable(value = "matches", key = "#season")
    public List<MatchStatistics> getMatchStatistics(String season) throws SQLException {
        return matchStatisticsDAO.getMatchStatistics();
    }

    @Cacheable(value = "match", key = "#season + '-' + #matchNo")
    public MatchStatistics getMatchStatistics(int matchNo, int season) throws SQLException {
        return matchStatisticsDAO.getMatchStatistics(matchNo);
    }
}
