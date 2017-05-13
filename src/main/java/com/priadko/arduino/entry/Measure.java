package com.priadko.arduino.entry;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Measure implements Serializable {
    private long id;
    private double value;
    private TypeMeasure typeMeasure;
    private Timestamp dateTime;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    public TypeMeasure getTypeMeasure() {
        return typeMeasure;
    }

    public void setTypeMeasure(TypeMeasure typeMeasure) {
        this.typeMeasure = typeMeasure;
    }

    @Version
    @Column
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
