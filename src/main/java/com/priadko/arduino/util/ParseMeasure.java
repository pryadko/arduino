package com.priadko.arduino.util;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.entry.TypeMeasure;

public final class ParseMeasure {
    public static Measure parseMeasure(String inputString) {
        if ((inputString.trim().length() == 0)) return null;

        Measure measure = new Measure();
        TypeMeasure typeMeasure = new TypeMeasure();
        measure.setTypeMeasure(typeMeasure);

        String[] st = inputString.split("=");
        if ((st.length != 2)) {
            throw new IllegalArgumentException("Incorrect string to parse = " + inputString);
        }
        String param = st[0].trim();
        String[] value = st[1].trim().split(" ");

        if ((value.length != 2)) {
            throw new IllegalArgumentException("Incorrect string to parse = " + inputString);
        }

        typeMeasure.setName(param);
        measure.setValue(Double.valueOf(value[0]));

        return measure;
    }
}
