package com.sportus.be.recommend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClovaAnalysisResponse(
        Status status,
        Result result
) {
    public record Status(
            String code,
            String message
    ) {

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Result(
            String finalAnswer // JSON 문자열로 수신
    ) {
        // JSON 문자열을 FinalAnswer 객체로 변환하는 메서드
        public FinalAnswer parseFinalAnswer() throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(finalAnswer, FinalAnswer.class);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FinalAnswer(
            Boolean isFacility,
            List<String> categories
    ) {}

    public List<String> categories() {
        try {
            return this.result.parseFinalAnswer().categories();
        } catch (IOException e) {
            return List.of(); // 빈 리스트 반환
        }
    }
}
