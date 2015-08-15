package com.priadko.arduino.entry;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name = "MEASURE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Measure implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="MEASURE_ID")
    private int id;

    @Column(name="VALUE")
    private double value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="type_measure_id")
    private TypeMeasure typeMeasure;

    @Column(name="DATA_TIME")
    @Type(type="timestamp")
    private java.sql.Timestamp dateTime;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public TypeMeasure getTypeMeasure() {
        return typeMeasure;
    }

    public void setTypeMeasure(TypeMeasure typeMeasure) {
        this.typeMeasure = typeMeasure;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
