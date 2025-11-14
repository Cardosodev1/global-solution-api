package com.global.solution.api.client;

import com.global.solution.api.client.rqrs.SkillExtractRQ;
import com.global.solution.api.client.rqrs.SkillExtractRS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SkillApiClient {

    private final WebClient webClient;
    private final String apiUrl;
    private final String apiKey;

    public SkillApiClient(WebClient webClient,
                          @Value("${ai.api.url}") String apiUrl,
                          @Value("${ai.api.key}") String apiKey) {
        this.webClient = webClient;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public SkillExtractRS extractSkills(String text) {
        SkillExtractRQ request = new SkillExtractRQ(text, this.apiKey);
        return this.webClient.post()
                .uri(this.apiUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SkillExtractRS.class)
                .block();
    }

}
