package com.example.cricketapp;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.cricketapp.keyspace.Keyspace;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.*;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.threadpool.ThreadPool;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;

@SpringBootApplication
public class CricketappApplication {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private static final Logger logger = LoggerFactory.getLogger(CricketappApplication.class);

	public static void main(String[] args) throws SQLException, IOException {

		SpringApplication.run(CricketappApplication.class, args);

		logger.info("Spring Boot application started now");

		DBService dbService = new DBService();
		dbService.importData("CricketApp");

		BasicCredentialsProvider credentials = new BasicCredentialsProvider();
		credentials.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "5va1IMbidUt_KglR2IDC"));

		RestClient restClient = RestClient
				.builder(HttpHost.create("http://localhost:9200"))
				.setHttpClientConfigCallback(hc -> hc.setDefaultCredentialsProvider(credentials))
				.build();

		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
		ElasticsearchClient client = new ElasticsearchClient(transport);

		HealthResponse healthResponse = client.cluster().health();
		System.out.printf("Elasticsearch status is: [%s]", healthResponse.status());
//		Keyspace.setSeason22();
//		dbService.createKeySpace(Keyspace.getKeyspaceName());
//		dbService.createTables();
//		dbService.importCSV();
//		dbService.createMaterializedViews();
//		dbService.createFunctions();
//		dbService.insertValues();
	}
}
