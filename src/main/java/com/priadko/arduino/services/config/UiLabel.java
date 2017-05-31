package com.priadko.arduino.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties()
@Component
public class UiLabel {
    private Map<String, String> toUi;

    public Map<String, String> getToUi() {
        return toUi;
    }

    public void setToUi(Map<String, String> toUi) {
        this.toUi = toUi;
    }
}
