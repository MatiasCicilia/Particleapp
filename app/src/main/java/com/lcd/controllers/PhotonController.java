package com.lcd.controllers;

import android.util.Log;

import com.lcd.models.AbstractVariable;
import com.lcd.models.Entity;
import com.lcd.models.ForeignVariable;
import com.lcd.models.InputConnection;
import com.lcd.models.OutputConnection;
import com.lcd.models.Variable;
import com.lcd.models.enums.ConnectionType;
import com.lcd.models.enums.Operator;
import com.lcd.models.enums.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleDevice;

/**
 * Created by Matias Cicilia on 20-Jun-17.
 */

public class PhotonController {
    private List<ForeignVariable> foreignVariables;
    private List<InputConnection> inputConnections;
    private List<OutputConnection> outputConnections;
    private List<Variable> variables;
    private List<ParticleDevice> devices;
    private static PhotonController instance;
    private static AtomicInteger id;

    private PhotonController() {
        foreignVariables = new ArrayList<>();
        inputConnections = new ArrayList<>();
        outputConnections = new ArrayList<>();
        variables = new ArrayList<>();
        id = new AtomicInteger();
    }

    public static PhotonController getInstance() {
        if (instance == null) {
            instance = new PhotonController();
        }
        return instance;
    }

    public void createForeignVariable(String deviceId, String name, int valId) {
        if(valId == -1){
            valId = id.incrementAndGet();
        }
        ForeignVariable foreignVariable = new ForeignVariable(id.incrementAndGet(),valId,deviceId,name);
        foreignVariables.add(foreignVariable);
    }

    public void createInputConnection(int inputId, ConnectionType input,String deviceId, String name) {
        InputConnection inputConnection = new InputConnection(id.incrementAndGet(),inputId, input, deviceId, name);
        inputConnections.add(inputConnection);
    }

    public void createOutputConnection(int outputId, ConnectionType outputType, int value, String deviceId, String name) {
        OutputConnection outputConnection = new OutputConnection(outputId, outputType, value, deviceId, name);
        outputConnections.add(outputConnection);
    }

    public void createVariable(int valId1, int valId2,
                               int inputConstant, Operator op, Result result,
                               int resultConstant, boolean global, String deviceId, String name) {
        int valId = id.incrementAndGet();
        valId1 = valId1==-1?valId:valId1;
        valId2 = valId2==-1?valId:valId2;

        Variable variable = new Variable(valId,valId1, valId2,
        inputConstant, op, result,
        resultConstant, global, deviceId, name);

        variables.add(variable);
    }

    public void configure(){
        for(ParticleDevice d: devices){
            sendInputs(d);
            sendGlobals(d);
            sendVariables(d);
            sendVariables(d);
            sendOutputs(d);
        }
    }

    private void sendGlobals(ParticleDevice device) {
        Log.d("Controller","Sending inputs");
        for(ForeignVariable f: foreignVariables){
            if(f.getDeviceId().equals(device.getName())){
                try {
                    Log.d("Controller","Call function response "+device.callFunction("global",f.toArgList()));
                } catch (ParticleCloudException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParticleDevice.FunctionDoesNotExistException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendInputs(ParticleDevice device) {
        Log.d("Controller","Sending inputs");
        for(InputConnection c: inputConnections){
            if(c.getDeviceId().equals(device.getName())){
                try {
                    Log.d("Controller","Sending input "+c.getName()+ " to "+c.getDeviceId());
                    Log.d("Controller","Call function response "+device.callFunction("input",c.toArgList()));

                } catch (ParticleCloudException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParticleDevice.FunctionDoesNotExistException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendVariables(ParticleDevice device) {
        for(Variable v: variables){
            if(v.getDeviceId().equals(device.getName())){
                try {
                    device.callFunction("variable",v.toArgList());
                } catch (ParticleCloudException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParticleDevice.FunctionDoesNotExistException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendOutputs(ParticleDevice device) {
        for(OutputConnection o: outputConnections){
            if(o.getDeviceId().equals(device.getName())){
                try {
                    device.callFunction("output",o.toArgList());
                } catch (ParticleCloudException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParticleDevice.FunctionDoesNotExistException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<AbstractVariable> getPossibleVariables(String deviceId) {
        List<AbstractVariable> result = new ArrayList<>();
        result.addAll(foreignVariables);
        for (InputConnection i: inputConnections) {
            if (i.getDeviceId().equals(deviceId)) result.add(i);
        }
        for (Variable v: variables) {
            if (v.getDeviceId().equals(deviceId)) result.add(v);
        }
        return result;
    }

    public List<Variable> getGlobalVariables() {
        List<Variable> result = new ArrayList<>();
        for (Variable v: variables) {
            if (v.isGlobal()) result.add(v);
        }
        return result;
    }

    public List<ParticleDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<ParticleDevice> devices) {
        this.devices = devices;
    }

    public List<ForeignVariable> getForeignVariables() {
        return foreignVariables;
    }

    public List<InputConnection> getInputConnections() {
        return inputConnections;
    }

    public List<OutputConnection> getOutputConnections() {
        return outputConnections;
    }

    public List<Variable> getVariables() {
        return variables;
    }
}
