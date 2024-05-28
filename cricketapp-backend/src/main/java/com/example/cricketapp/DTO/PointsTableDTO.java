package com.example.cricketapp.DTO;

import com.example.cricketapp.Model.MatchStatistics;
import com.example.cricketapp.Model.PointsTableData;

import java.util.List;

public class PointsTableDTO {
    private MatchStatistics matchStatistics;
    private List<PointsTableData> pointsTableData;

    public PointsTableDTO() {}

    public PointsTableDTO(MatchStatistics matchStatistics, List<PointsTableData> pointsTableData) {
        this.matchStatistics = matchStatistics;
        this.pointsTableData = pointsTableData;
    }

    public MatchStatistics getMatchStatistics() {
        return matchStatistics;
    }

    public void setMatchStatistics(MatchStatistics matchStatistics) {
        this.matchStatistics = matchStatistics;
    }

    public List<PointsTableData> getPointsTableData() {
        return pointsTableData;
    }

    public void setPointsTableData(List<PointsTableData> pointsTableData) {
        this.pointsTableData = pointsTableData;
    }

    @Override
    public String toString() {
        return "PointsTableDTO{" +
                "matchStatistics=" + matchStatistics +
                ", pointsTableData=" + pointsTableData +
                '}';
    }
}
