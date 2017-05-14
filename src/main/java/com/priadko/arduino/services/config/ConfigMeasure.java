package com.priadko.arduino.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("Measure")
@Component
public class ConfigMeasure implements Config {
    private List<ConfigEntry> list = new ArrayList<>();

    public List<ConfigEntry> getList() {
        return list;
    }

    @Override
    public ConfigEntry findConfigByName(String s) {

        return list.stream()
                .filter(configEntry -> configEntry.getName().equals(s))
                .findAny()
                .orElseGet(ConfigEntry::new);
    }
}
