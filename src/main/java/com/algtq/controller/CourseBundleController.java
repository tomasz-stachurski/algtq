package com.algtq.controller;

import com.algtq.dto.TopicsRequestDto;
import com.algtq.service.CourseBundleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CourseBundleController {

    private final CourseBundleService service;

    public CourseBundleController(CourseBundleService service) {
        this.service = service;
    }

    @PostMapping("/bundle")
    public Map<String, Double> getCourseBundles(@RequestBody @Valid TopicsRequestDto topicsRequestDto) {
        return service.getCourseBundles(topicsRequestDto);
    }
}
