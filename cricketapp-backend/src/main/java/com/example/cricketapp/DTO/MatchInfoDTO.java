package com.example.cricketapp.DTO;

import com.example.cricketapp.Model.MatchData;
import com.example.cricketapp.Model.MatchStatistics;

public class MatchInfoDTO {
    private MatchStatistics matchStatistics;
    private MatchData matchData;

    public MatchInfoDTO() {}

    public MatchInfoDTO(MatchStatistics matchStatistics, MatchData matchData) {
        this.matchStatistics = matchStatistics;
        this.matchData = matchData;
    }

    public MatchStatistics getMatchStatistics() {
        return matchStatistics;
    }

    public void setMatchStatistics(MatchStatistics matchStatistics) {
        this.matchStatistics = matchStatistics;
    }

    public MatchData getMatchData() {
        return matchData;
    }

    public void setMatchData(MatchData matchData) {
        this.matchData = matchData;
    }

    @Override
    public String toString() {
        return "MatchInfoDTO{" +
                "matchStatistics=" + matchStatistics +
                ", matchData=" + matchData +
                '}';
    }
}
