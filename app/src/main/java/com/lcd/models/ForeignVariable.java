package com.lcd.models;

/**
 * Created by matia on 20-Jun-17.
 */

public class ForeignVariable extends AbstractVariable{

    private int remoteValId;

    public ForeignVariable(int valId,int remoteValId,String deviceId, String name) {
        super(valId,deviceId, name);
        this.remoteValId = remoteValId;
    }
}
