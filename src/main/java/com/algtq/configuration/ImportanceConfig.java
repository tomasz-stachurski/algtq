package com.algtq.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "importance")
@Getter
@Setter
public class ImportanceConfig {
    private Integer top1;
    private Integer top2;
    private Integer top3;
    private Integer doubleMatch;
}
