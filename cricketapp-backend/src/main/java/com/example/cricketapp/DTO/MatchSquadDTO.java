package com.example.cricketapp.DTO;

import com.example.cricketapp.Model.MatchStatistics;

import java.util.List;

public class MatchSquadDTO {
    private MatchStatistics matchStatistics;
    private List<List<String>> squads;

    public MatchSquadDTO() {}

    public MatchSquadDTO(MatchStatistics matchStatistics, List<List<String>> squads) {
        this.matchStatistics = matchStatistics;
        this.squads = squads;
    }

    public MatchStatistics getMatchStatistics() {
        return matchStatistics;
    }

    public void setMatchStatistics(MatchStatistics matchStatistics) {
        this.matchStatistics = matchStatistics;
    }

    public List<List<String>> getSquads() {
        return squads;
    }

    public void setSquads(List<List<String>> squads) {
        this.squads = squads;
    }

    @Override
    public String toString() {
        return "MatchSquadDTO{" +
                "matchStatistics=" + matchStatistics +
                ", squads=" + squads +
                '}';
    }
}
