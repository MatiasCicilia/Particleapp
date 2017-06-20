package com.lcd.models;

/**
 * Created by Matias Cicilia on 20-Jun-17.
 */

public class Entity {
    private String deviceId;
    private String name;

    public Entity(String deviceId, String name) {
        this.name = name;
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
