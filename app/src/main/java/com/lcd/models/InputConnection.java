package com.lcd.models;

import com.lcd.models.enums.ConnectionType;

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

    public int getInputId() {
        return inputId;
    }

    public void setInputId(int inputId) {
        this.inputId = inputId;
    }

    public ConnectionType getInput() {
        return input;
    }

    public void setInput(ConnectionType input) {
        this.input = input;
    }


}
