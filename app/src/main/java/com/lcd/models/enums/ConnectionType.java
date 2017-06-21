package com.lcd.models.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matia on 20-Jun-17.
 */

public enum ConnectionType {
    ANALOG,
    DIGITAL,
    UP_FLANK;

    public static List<String> getListValues() {
        ConnectionType[] enums = values();
        ArrayList<String> enumsAsString = new ArrayList<>();
        for (ConnectionType r: enums) {
            enumsAsString.add(r.toString());
        }
        return enumsAsString;
    }
}
