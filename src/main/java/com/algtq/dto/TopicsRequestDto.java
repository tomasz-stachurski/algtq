package com.algtq.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicsRequestDto {
    @Valid
    @NotNull
    private Topics topics;

    public Map<String, Integer> getTop3() {
        Map<String, Integer> fieldsMap = new HashMap<>();
        fieldsMap.put("reading", topics.getReading());
        fieldsMap.put("math", topics.getMath());
        fieldsMap.put("science", topics.getScience());
        fieldsMap.put("history", topics.getHistory());
        fieldsMap.put("art", topics.getArt());

        return fieldsMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Data
    @Builder
    public static class Topics {
        @NotNull
        private Integer reading;
        @NotNull
        private Integer math;
        @NotNull
        private Integer science;
        @NotNull
        private Integer history;
        @NotNull
        private Integer art;
    }

}
