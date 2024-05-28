package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.MatchDetailsDAO;
import com.example.cricketapp.Model.MatchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MatchDetailsService {
    @Autowired
    MatchDetailsDAO matchDetailsDAO;

    @Cacheable(value = "info", key = "#season + '-' + #matchNo")
    public MatchData getMatchDetails(int matchNo, int season) {
        return matchDetailsDAO.getMatchDetails(matchNo);
    }
}
