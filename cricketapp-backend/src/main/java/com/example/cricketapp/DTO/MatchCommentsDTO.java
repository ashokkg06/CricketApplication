package com.example.cricketapp.DTO;

import com.example.cricketapp.Model.MatchComments;
import com.example.cricketapp.Model.MatchStatistics;

import java.util.List;

public class MatchCommentsDTO {
    private MatchStatistics matchStatistics;

    private List<List<MatchComments>> matchComments;

    public MatchCommentsDTO() {}

    public MatchCommentsDTO(MatchStatistics matchStatistics, List<List<MatchComments>> matchComments) {
        this.matchStatistics = matchStatistics;
        this.matchComments = matchComments;
    }

    public MatchStatistics getMatchStatistics() {
        return matchStatistics;
    }

    public void setMatchStatistics(MatchStatistics matchStatistics) {
        this.matchStatistics = matchStatistics;
    }

    public List<List<MatchComments>> getMatchComments() {
        return matchComments;
    }

    public void setMatchComments(List<List<MatchComments>> matchComments) {
        this.matchComments = matchComments;
    }

    @Override
    public String toString() {
        return "MatchCommentsDTO{" +
                "matchStatistics=" + matchStatistics +
                ", matchComments=" + matchComments +
                '}';
    }
}
