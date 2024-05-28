package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.PlayerSquadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerSquadService {

    @Autowired
    PlayerSquadDAO playerSquadDAO;

    @Cacheable(value = "squad", key = "#season + '-' + #matchNo")
    public List<List<String>> getSquad(int matchNo, int season) {
        return playerSquadDAO.getSquad(matchNo);
    }
}
