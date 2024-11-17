package com.algtq.controller;

import com.algtq.dto.TopicsRequestDto;
import com.algtq.service.CourseBundleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseBundleControllerTest {

    @Mock
    private CourseBundleService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CourseBundleController controller = new CourseBundleController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetCourseBundles_ValidRequest() throws Exception {
        // Given
        TopicsRequestDto.Topics topics = TopicsRequestDto.Topics.builder()
                .art(10)
                .math(20)
                .history(30)
                .reading(40)
                .science(50)
                .build();
        TopicsRequestDto topicsRequestDto = TopicsRequestDto.builder().topics(topics).build();

        Map<String, Double> serviceResponse = new HashMap<>();
        serviceResponse.put("provider1", 10.0);

        when(service.getCourseBundles(topicsRequestDto)).thenReturn(serviceResponse);

        // When Then
        mockMvc.perform(post("/bundle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "topics": {
                            "art": 10,
                            "math": 20,
                            "history": 30,
                            "reading": 40,
                            "science": 50
                        }
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.provider1").value(10.0));
    }

    @Test
    void testGetCourseBundles_OneTopicIsNull() throws Exception {
        // When & Then
        mockMvc.perform(post("/bundle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "topics": {
                                    "reading": null,
                                    "math": 50,
                                    "science": 30,
                                    "history": 15,
                                    "art": 10
                                    }
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCourseBundles_OneTopicIsNotNumeric() throws Exception {
        // When & Then
        mockMvc.perform(post("/bundle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "topics": {
                                    "reading": "reading",
                                    "math": 50,
                                    "science": 30,
                                    "history": 15,
                                    "art": 10
                                    }
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCourseBundles_NoTopics() throws Exception {
        // When & Then
        mockMvc.perform(post("/bundle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "topics": {}
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCourseBundles_EmptyRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/bundle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

}