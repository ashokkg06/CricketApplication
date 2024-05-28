package com.example.cricketapp.Service;

import com.example.cricketapp.DAO.PointsTableDAO;
import com.example.cricketapp.Model.PointsTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PointsTableService {

    @Autowired
    PointsTableDAO pointsTableDAO;

    @Cacheable(value = "pointstable", key = "#season")
    public List<PointsTableData> getPointsTable(int season) {
        List<PointsTableData> list = pointsTableDAO.getPointsTable();
        List<Long> sortlist = new ArrayList<>();
        for(PointsTableData data: list) {
            sortlist.add(data.getPoints());
        }
        sortlist.sort((a, b) -> {
            if (a < b) return 1;
            else if (a > b) return -1;
            else return 0;
        });

        List<PointsTableData> result = new ArrayList<>();
        Long last = 0L;
        for(Long num: sortlist) {
            if(num==last) continue;
            for(PointsTableData data: list) {
                if(data.getPoints() == num) {
                    result.add(data);
                }
            }
            last = num;
        }
        return result;
    }
}