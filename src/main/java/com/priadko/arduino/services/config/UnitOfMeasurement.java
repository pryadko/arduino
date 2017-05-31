package com.priadko.arduino.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties()
@Component
public class UnitOfMeasurement {
    private Map<String, Double> transformValue;

    public Map<String, Double> getTransformValue() {
        return transformValue;
    }

    public void setTransformValue(Map<String, Double> transformValue) {
        this.transformValue = transformValue;
    }
}
