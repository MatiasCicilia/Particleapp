package com.lcd.models;

import com.lcd.models.enums.ConnectionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matia on 20-Jun-17.
 */

public class OutputConnection extends Entity {
    private int outputId; //Patita ID
    private ConnectionType outputType; //Type of output
    private int valId;

    public OutputConnection(int outputId, ConnectionType outputType, int valId, String deviceId, String name) {
        super(deviceId, name);
        this.outputId = outputId;
        this.outputType = outputType;
        this.valId = valId;
    }

    public int getOutputId() {
        return outputId;
    }

    public ConnectionType getOutputType() {
        return outputType;
    }

    public int getValId() {
        return valId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutputConnection that = (OutputConnection) o;

        return outputId == that.outputId && getDeviceId().equals(that.getDeviceId());

    }

    @Override
    public int hashCode() {
        int result = outputId;
        result = 31 * result + getDeviceId().hashCode();
        return result;
    }

    public List<String> toArgList(){
        List<String> argList = new ArrayList<>();
        argList.add(getValId()+"");
        argList.add(getOutputId()+"");
        argList.add(outputType.ordinal()+"");
        return argList;
    }
}
