package com.priadko.arduino.entry;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class UiConfig {
    private Map<String, Double> initialValues = new TreeMap<>();

    public Map<String, Double> getInitialValues() {
        return initialValues;
    }

    public void putInitialValue(String name, Double value) {
        initialValues.put(name, value);
    }

    public Double getInitialValue(String name) {
        return initialValues.getOrDefault(name, Double.NEGATIVE_INFINITY);
    }
}
