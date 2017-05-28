package com.priadko.arduino.entry;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class UnitOfMeasurement implements Serializable {
    private int id;
    private String name;
    private Set<TypeMeasure> typeMeasureSet;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @Transient
    public Set<TypeMeasure> getTypeMeasureSet() {
        return typeMeasureSet;
    }

    public void setTypeMeasureSet(Set<TypeMeasure> typeMeasureSet) {
        this.typeMeasureSet = typeMeasureSet;
    }
}
