package com.priadko.arduino.web.controller;

import com.priadko.arduino.entry.UiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ConfigurationController {

    private final UiConfig uiConfig;

    @Autowired
    public ConfigurationController(UiConfig uiConfig) {
        this.uiConfig = uiConfig;
    }

    @MessageMapping("/initial")
    @SendTo("/measure/initial")
    public UiConfig initial(String initialMessage) {
        
        return uiConfig;
    }
}
