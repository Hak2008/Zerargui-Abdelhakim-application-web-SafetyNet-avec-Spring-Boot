package com.safetynet.alerts.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataFetcher {
    private final RestTemplate restTemplate;
    private final String dataUrl;

    @Autowired
    public DataFetcher(RestTemplate restTemplate, @Value("${data.url}") String dataUrl) {
        this.restTemplate = restTemplate;
        this.dataUrl = dataUrl;
    }

    public AppData fetchDataFromUrl() {
        ResponseEntity<AppData> responseEntity = restTemplate.getForEntity(dataUrl, AppData.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new IllegalStateException("Failed to fetch data from URL: " + dataUrl);
        }
    }
}

