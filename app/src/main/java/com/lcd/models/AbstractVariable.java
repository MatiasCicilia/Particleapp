package com.lcd.models;

/**
 * Created by Matias Cicilia on 20-Jun-17.
 */

public class AbstractVariable extends Entity{
    private int valId;
    private int value;

    public AbstractVariable(int valId, int value, String deviceId, String name) {
        super(deviceId, name);
        this.valId = valId;
        this.value = value;
    }

    public int getValId() {
        return valId;
    }

    public void setValId(int valId) {
        this.valId = valId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
