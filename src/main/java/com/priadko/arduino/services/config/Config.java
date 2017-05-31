package com.priadko.arduino.services.config;

import java.util.List;

public interface Config {
    ConfigEntry findConfigByName(String s);

    List<ConfigEntry> getList();
}
