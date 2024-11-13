package kg.project.megatest.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExternalApiServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiServiceImpl.class);

    private final WebClient webClient;

    @Autowired
    public ExternalApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.restful-api.dev/objects").build();
    }


    @Transactional
    public String  fetchAndLogData() {
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
