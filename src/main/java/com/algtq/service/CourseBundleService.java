package com.algtq.service;

import com.algtq.configuration.ImportanceConfig;
import com.algtq.dto.TopicsRequestDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseBundleService {

    private final Map<String, String> providerTopics;

    private final ImportanceConfig importanceConfig;

    public CourseBundleService(Map<String, String> providerTopics, ImportanceConfig importanceConfig) {
        this.providerTopics = providerTopics;
        this.importanceConfig = importanceConfig;
    }

    public Map<String, Double> getCourseBundles(TopicsRequestDto topicsRequestDto) {
        return calculateQuotes(topicsRequestDto.getTop3(), providerTopics);
    }

    private Map<String, Double> calculateQuotes(Map<String, Integer> top3, Map<String, String> providerTopics) {
        Map<String, Double> calculatedQuotes = new LinkedHashMap<>();
        List<String> keys = new ArrayList<>(top3.keySet());

        providerTopics.forEach((key, value) -> {
            String[] divided = value.split("\\+");
            if (divided.length == 2) {
                String topic1 = divided[0];
                String topic2 = divided[1];

                if (top3.containsKey(divided[0]) && top3.containsKey(divided[1])) {
                    double result = calculateDoubleMatch(top3.get(topic1), top3.get(topic2));
                    if (result != 0) {
                        calculatedQuotes.put(key, result);
                    }
                } else {
                    addSingleMatchResult(calculatedQuotes, key, topic1, keys, top3);
                    addSingleMatchResult(calculatedQuotes, key, topic2, keys, top3);
                }
            }
        });

        return calculatedQuotes;
    }

    private Double calculateSingleMatch(String key, List<String> keys, Map<String, Integer> top3) {
        int index = keys.indexOf(key);
        return (double) (top3.get(key) * getSingleMatchImportance(index) / 100);
    }

    private Integer getSingleMatchImportance(int index) {
        return switch (index) {
            case 0 -> importanceConfig.getTop1();
            case 1 -> importanceConfig.getTop2();
            case 2 -> importanceConfig.getTop3();
            default -> 0;
        };
    }

    private double calculateDoubleMatch(int value1, int value2) {
        return (value1 + value2) * importanceConfig.getDoubleMatch() / 100.0;
    }

    private void addSingleMatchResult(Map<String, Double> calculatedQuotes, String provider, String topic, List<String> keys, Map<String, Integer> top3) {
        if (top3.containsKey(topic)) {
            double result = calculateSingleMatch(topic, keys, top3);
            if (result != 0) {
                calculatedQuotes.put(provider, result);
            }
        }
    }
}
