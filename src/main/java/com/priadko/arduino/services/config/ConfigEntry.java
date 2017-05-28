package com.priadko.arduino.services.config;

import java.util.List;
import java.util.Objects;

public class ConfigEntry {
    private int id;
    private String name;
    private int order;
    private boolean allow;
    private String unitOfMeasurementIn;
    private String unitOfMeasurementOut;
    private List<String> convertTo;
    private double delta;


    public boolean isChangedEnough(Double oldValue, Double newValue) {

        return Math.abs(oldValue - newValue) >= delta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    public String getUnitOfMeasurementIn() {
        return unitOfMeasurementIn;
    }

    public void setUnitOfMeasurementIn(String unitOfMeasurementIn) {
        this.unitOfMeasurementIn = unitOfMeasurementIn;
    }

    public String getUnitOfMeasurementOut() {
        return unitOfMeasurementOut;
    }

    public void setUnitOfMeasurementOut(String unitOfMeasurementOut) {
        this.unitOfMeasurementOut = unitOfMeasurementOut;
    }

    public List<String> getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(List<String> convertTo) {
        this.convertTo = convertTo;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigEntry that = (ConfigEntry) o;
        return order == that.order &&
                allow == that.allow &&
                Double.compare(that.delta, delta) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(unitOfMeasurementIn, that.unitOfMeasurementIn) &&
                Objects.equals(unitOfMeasurementOut, that.unitOfMeasurementOut) &&
                Objects.equals(convertTo, that.convertTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, order, allow, unitOfMeasurementIn, unitOfMeasurementOut, convertTo, delta);
    }
}
