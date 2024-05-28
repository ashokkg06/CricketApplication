package com.example.cricketapp.Model;

public class DismissalsEntity {

    private String batter;
    private String dismissals;

    public String getBatter() {
        return batter;
    }

    public void setBatter(String batter) {
        this.batter = batter;
    }

    public String getDismissals() {
        return dismissals;
    }

    public void setDismissals(String dismissals) {
        this.dismissals = dismissals;
    }

    @Override
    public String toString() {
        return "DismissalsEntity{" +
                "batter='" + batter + '\'' +
                ", dismissals='" + dismissals + '\'' +
                '}';
    }
}
