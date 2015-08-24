package com.priadko.arduino.services;

import java.util.List;

public interface DataService {

    void writeMeasure(String string);

    void createTypeMeasure(String name);

    List getAllByType(String name);

    List getAllTypeMeasure();

}
