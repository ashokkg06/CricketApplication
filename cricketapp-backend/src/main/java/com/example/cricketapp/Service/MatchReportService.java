package com.example.cricketapp.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.cricketapp.Model.MatchReport;
import com.example.cricketapp.Model.PerfMonReport;
import com.example.cricketapp.config.ElasticSearchConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MatchReportService {
    private static final ElasticsearchClient client = ElasticSearchConfig.getClient();
    public List<MatchReport> getMatchReports(String request) throws IOException {
        SearchResponse<MatchReport> searchResponse = client.search(s -> s
                .index("reports")
                .size(1000)
                .query(q -> q
                        .match(m -> m
                                .field("URL")
                                .query(request))), MatchReport.class);

        List<Hit<MatchReport>> list = searchResponse.hits().hits();
        List<MatchReport> result = new ArrayList<>();
        for(int i=0; i<list.size(); i++) result.add(list.get(i).source());
        return result;
    }

    public List<PerfMonReport> getPerfMonReports(String request) throws IOException {
        SearchResponse<PerfMonReport> searchResponse = client.search(s -> s
                .index("perfmonreport")
                .size(100)
                .query(q -> q
                        .match(m -> m
                                .field("URL")
                                .query(request))), PerfMonReport.class);

        List<Hit<PerfMonReport>> list = searchResponse.hits().hits();
        List<PerfMonReport> result = new ArrayList<>();
        for(int i=0; i<list.size(); i++) result.add(list.get(i).source());
        return result;
    }
}
