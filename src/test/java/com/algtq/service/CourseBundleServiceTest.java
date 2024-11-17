package com.algtq.service;

import com.algtq.configuration.ImportanceConfig;
import com.algtq.dto.TopicsRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CourseBundleServiceTest {

    private CourseBundleService service;

    @Mock
    private ImportanceConfig importanceConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Map<String, String> providerTopics = new HashMap<>();
        providerTopics.put("provider1", "math+science");
        providerTopics.put("provider2", "reading+science");
        providerTopics.put("provider3", "history+math");
        providerTopics.put("provider4", "reading+art");

        when(importanceConfig.getDoubleMatch()).thenReturn(10);
        when(importanceConfig.getTop1()).thenReturn(20);
        when(importanceConfig.getTop2()).thenReturn(25);
        when(importanceConfig.getTop3()).thenReturn(30);

        service = new CourseBundleService(providerTopics, importanceConfig);
    }

    @Test
    void shouldCalculatedAllProviders() {
        // Given
        TopicsRequestDto.Topics topics = TopicsRequestDto.Topics.builder()
                .art(10)
                .math(20)
                .history(30)
                .reading(40)
                .science(50)
                .build();
        TopicsRequestDto topicsRequestDto = TopicsRequestDto.builder().topics(topics).build();

        // When
        Map<String, Double> result = service.getCourseBundles(topicsRequestDto);

        // Then
        assertEquals(4, result.size());
        assertEquals(10.0, result.get("provider1"));
        assertEquals(9.0, result.get("provider2"));
        assertEquals(9.0, result.get("provider3"));
        assertEquals(10.0, result.get("provider4"));
    }

    @Test
    void shouldNotCalculatedAnyProvider() {
        // Given
        TopicsRequestDto.Topics topics = TopicsRequestDto.Topics.builder()
                .art(0)
                .math(0)
                .history(0)
                .reading(0)
                .science(0)
                .build();
        TopicsRequestDto topicsRequestDto = TopicsRequestDto.builder().topics(topics).build();

        // When
        Map<String, Double> result = service.getCourseBundles(topicsRequestDto);

        // Then
        assertEquals(0, result.size());
    }

}