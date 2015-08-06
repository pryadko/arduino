package com.priadko.arduino.entry;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "MEASURE")
public class Measure {
    @Id
    @GeneratedValue
    @Column(name="MEASURE_ID")
    private int id;

    @Column(name="VALUE")
    private double value;

    @ManyToOne
    @JoinColumn(name="TYPE_MEASURE_ID")
    private TypeMeasure typeMeasure;

    @Column(name="DATA_TIME")
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
