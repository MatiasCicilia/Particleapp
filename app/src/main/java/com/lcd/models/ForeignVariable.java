package com.lcd.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matia on 20-Jun-17.
 */

public class ForeignVariable extends AbstractVariable{

    private int remoteValId;
    private boolean headless;

    public ForeignVariable(int valId,int remoteValId,String deviceId, String name) {
        super(valId,deviceId, name);
        this.remoteValId = remoteValId;
    }

    public ForeignVariable(String deviceId, String name) {
        super(deviceId, name);
    }

    public int getRemoteValId() {
        return remoteValId;
    }

    public List<String> toArgList(){
        List<String> argList = new ArrayList<>();
        argList.add(getValId()+"");
        argList.add(getRemoteValId()+"");
        return argList;
    }

    @Override
    public String toString() {
        return "ForeignVariable{" +
                "remoteValId=" + remoteValId +
                ", headless=" + headless +
                '}';
    }
}
