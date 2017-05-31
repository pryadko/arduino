package com.priadko.arduino.services.observer;

import com.priadko.arduino.services.MeasureData;
import com.priadko.arduino.services.config.ConfigEntry;
import com.priadko.arduino.services.config.UiLabel;
import com.priadko.arduino.services.config.UnitOfMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Fabrica {
    @Autowired
    private UiLabel uiLabel;
    @Autowired
    private UnitOfMeasurement unitOfMeasurement;

    public MeasureData create(ConfigEntry configEntry) {

        MeasureData measureData = new MeasureData(configEntry, uiLabel.getToUi(), unitOfMeasurement.getTransformValue() );
        return measureData;
    }
}
