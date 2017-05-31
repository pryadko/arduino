package com.priadko.arduino.services;

import com.priadko.arduino.services.config.ConfigEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MeasureData {

    private Integer id;
    private String name;
    private Double value;
    private String unitOfMeas;
    private ConfigEntry config;
    private Map<String, String> toUi;
    private Map<String, Double> convertValue;


    public MeasureData(ConfigEntry config, Map<String, String> toUi, Map<String, Double> convertValue) {
        id = config.getId();
        name = config.getName();
        value = Double.NEGATIVE_INFINITY;
        unitOfMeas = config.getUnitOfMeasurementOut();
        this.config = config;
        this.toUi = toUi;
        this.convertValue = convertValue;
    }

    public Map<String, Object> getUpdate() {
        HashMap<String, Object> measureValue = new HashMap<>();

        measureValue.put("id", id);
        measureValue.put("value", convertValue(value));

        return measureValue;
    }

    public Map<String, Object> getMessageForInit() {
        Map<String, Object> init = getUpdate();

        init.put("name", name);
        init.put("unitOfMeas", toUi.get(unitOfMeas));

        return init;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnitOfMeas() {
        return unitOfMeas;
    }

    public void setUnitOfMeas(String unitOfMeas) {
        this.unitOfMeas = unitOfMeas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasureData that = (MeasureData) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(unitOfMeas, that.unitOfMeas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unitOfMeas);
    }

    public void updateValue(double value) {
        this.value = value;
    }

    public boolean isChangedEnough(double newValue) {
        return value == Double.NEGATIVE_INFINITY || config.isChangedEnough(convertValue(this.value), convertValue(newValue));
    }

    private double convertValue(double value) {

        return value * convertValue.get(config.getUnitOfMeasurementIn() + unitOfMeas);

    }
}
