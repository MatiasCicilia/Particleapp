package com.lcd.models.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matia on 20-Jun-17.
 */

public enum Result {
    INPUT1,
    INPUT2,
    OPERATOR_RESULT,
    CONSTANT;

    public static List<String> getListValues() {
        Result[] enums = values();
        ArrayList<String> enumsAsString = new ArrayList<>();
        for (Result r: enums) {
            enumsAsString.add(r.toString());
        }
        return enumsAsString;
    }

}

