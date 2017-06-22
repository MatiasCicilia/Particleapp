package com.lcd.models;

/**
 * Created by Matias Cicilia on 20-Jun-17.
 */

public class AbstractVariable extends Entity{
    private int valId;

    public AbstractVariable(int valId,String deviceId, String name) {
        super(deviceId, name);
        this.valId = valId;
    }

    public AbstractVariable(String deviceId, String name) {
        super(deviceId, name);
    }

    public int getValId() {
        return valId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractVariable that = (AbstractVariable) o;

        return valId == that.valId;

    }

    @Override
    public int hashCode() {
        return valId;
    }
}
