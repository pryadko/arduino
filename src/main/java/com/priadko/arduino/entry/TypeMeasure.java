package com.priadko.arduino.entry;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TYPE_MEASURE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TypeMeasure implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="TYPE_MEASURE_ID")
    private int id;

    @Column(name="TYPE_MEASURE_NAME", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeMeasure")
    @JsonIgnore
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

    public Set<Measure> getMeasureSet() {
        return measureSet;
    }

    public void setMeasureSet(Set<Measure> measureSet) {
        this.measureSet = measureSet;
    }
}
