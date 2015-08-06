package com.priadko.arduino.entry;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TYPE_MEASURE")
public class TypeMeasure {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    private Set<Measure> measureSet;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "TY")
    public Set<Measure> getMeasureSet() {
        return measureSet;
    }

    public void setMeasureSet(Set<Measure> measureSet) {
        this.measureSet = measureSet;
    }
}
