package com.global.solution.api.client.rqrs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillExtractRQ {

    private String text;

    @JsonProperty("api_key")
    private String apiKey;

}
