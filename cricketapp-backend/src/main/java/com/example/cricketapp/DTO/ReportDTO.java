package com.example.cricketapp.DTO;

import com.example.cricketapp.Model.MatchReport;
import com.example.cricketapp.Model.PerfMonReport;
import java.util.List;
public class ReportDTO {
    private List<MatchReport> matchReport;

    private List<PerfMonReport> perfMonReport;

    public ReportDTO() {}

    public ReportDTO(List<MatchReport> matchReport, List<PerfMonReport> perfMonReport) {
        this.matchReport = matchReport;
        this.perfMonReport = perfMonReport;
    }

    public List<MatchReport> getMatchReport() {
        return matchReport;
    }

    public void setMatchReport(List<MatchReport> matchReport) {
        this.matchReport = matchReport;
    }

    public List<PerfMonReport> getPerfMonReport() {
        return perfMonReport;
    }

    public void setPerfMonReport(List<PerfMonReport> perfMonReport) {
        this.perfMonReport = perfMonReport;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "matchReport=" + matchReport +
                ", perfMonReport=" + perfMonReport +
                '}';
    }
}
