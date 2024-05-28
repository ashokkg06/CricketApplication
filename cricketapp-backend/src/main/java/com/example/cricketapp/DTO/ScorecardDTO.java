package com.example.cricketapp.DTO;

import com.example.cricketapp.Model.*;

import java.util.HashMap;
import java.util.List;

public class ScorecardDTO {
    private MatchStatistics matchStatistics;
    private List<List<BatterMatchStats>> batting;
    private List<List<BowlerMatchStats>> bowling;
    private List<ExtrasData> extras;

    private List<TotalData> total;

    public ScorecardDTO() {
    }

    public ScorecardDTO(MatchStatistics matchStatistics, List<List<BatterMatchStats>> batting, List<List<BowlerMatchStats>> bowling, List<ExtrasData> extras, List<TotalData> total) {
        this.matchStatistics = matchStatistics;
        this.batting = batting;
        this.bowling = bowling;
        this.extras = extras;
        this.total = total;
    }

    public MatchStatistics getMatchStatistics() {
        return matchStatistics;
    }

    public void setMatchStatistics(MatchStatistics matchStatistics) {
        this.matchStatistics = matchStatistics;
    }

    public List<List<BatterMatchStats>> getBatting() {
        return batting;
    }

    public void setBatting(List<List<BatterMatchStats>> batting) {
        this.batting = batting;
    }

    public List<List<BowlerMatchStats>> getBowling() {
        return bowling;
    }

    public void setBowling(List<List<BowlerMatchStats>> bowling) {
        this.bowling = bowling;
    }

    public List<ExtrasData> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasData> extras) {
        this.extras = extras;
    }

    public List<TotalData> getTotal() {
        return total;
    }

    public void setTotal(List<TotalData> total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ScorecardDTO{" +
                "matchStatistics=" + matchStatistics +
                ", batting=" + batting +
                ", bowling=" + bowling +
                ", extras=" + extras +
                ", total=" + total +
                '}';
    }
}
