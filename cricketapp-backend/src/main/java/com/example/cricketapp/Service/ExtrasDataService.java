package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.ExtrasDAO;
import com.example.cricketapp.Model.ExtrasData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtrasDataService {

    @Autowired
    ExtrasDAO extrasDAO;
    @Cacheable(value = "extras", key = "#season + '-' + #matchNo")
    public List<ExtrasData> getExtrasData(int matchNo, int season) {
        return extrasDAO.getExtrasData(matchNo);
    }
}
