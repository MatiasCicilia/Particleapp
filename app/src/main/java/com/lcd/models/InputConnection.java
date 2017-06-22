package com.lcd.models;

import com.lcd.models.enums.ConnectionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matia on 20-Jun-17.
 */

public class InputConnection extends AbstractVariable {
    private int inputId;  //Patita
    private ConnectionType input; //Type of input

    public InputConnection(int valId,int inputId, ConnectionType input, String deviceId, String name) {
        super(valId,deviceId, name);
        this.inputId = inputId;
        this.input = input;
    }

    public InputConnection(String deviceId, String name) {
        super(deviceId, name);
    }

    public int getInputId() {
        return inputId;
    }

    public ConnectionType getInput() {
        return input;
    }

    public List<String> toArgList(){
        List<String> argList = new ArrayList<>();
        argList.add(getValId()+"");
        argList.add(getInputId()+"");
        argList.add(input.ordinal()+"");
        return argList;
    }
}
