package com.example.cricketapp.Model;

public class MatchComments {
    private Float overs;
    private String outcome;
    private String comment;

    public Float getOvers() {
        return overs;
    }

    public void setOvers(Float overs) {
        this.overs = overs;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MatchComments{" +
                "overs=" + overs +
                ", outcome='" + outcome + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
