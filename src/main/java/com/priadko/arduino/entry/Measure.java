package com.priadko.arduino.entry;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "MEASURE")
public class Measure {
    @Id
    @GeneratedValue
    private int id;

    private double value;
    private TypeMeasure typeMeasure;
    private Date dateTime;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "", nullable = false)
    public TypeMeasure getTypeMeasure() {
        return typeMeasure;
    }

    public void setTypeMeasure(TypeMeasure typeMeasure) {
        this.typeMeasure = typeMeasure;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
