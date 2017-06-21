package com.lcd.models.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matia on 20-Jun-17.
 */

public enum Operator {
    EQUAL,
    GREATER,
    LESSER,
    AND_OP,
    OR_OP,
    XOR_OP,
    NOT_OP;

    public static List<String> getListValues() {
        Operator[] enums = values();
        ArrayList<String> enumsAsString = new ArrayList<>();
        for (Operator r: enums) {
            enumsAsString.add(r.toString());
        }
        return enumsAsString;
    }
}
