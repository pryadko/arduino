package com.priadko.arduino.util;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;

public final class ParseMeasure {
    public static Measure parseMeasure(String inputString) {
        Measure measure = new Measure();
        TypeMeasure typeMeasure = new TypeMeasure();
        measure.setTypeMeasure(typeMeasure);

        String[] st = inputString.split("=");
        if ((st.length != 2)) return null;
        String param = st[0].trim();
        String value = st[1].trim().split(" ")[0];

        typeMeasure.setName(param);
        measure.setValue(Double.valueOf(value));

        return measure;
    }
}
