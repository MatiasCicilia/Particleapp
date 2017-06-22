package com.lcd.models;

import com.lcd.models.enums.Operator;
import com.lcd.models.enums.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Cicilia on 20-Jun-17.
 */

public class Variable extends AbstractVariable {
    private int valId1;
    private int valId2;
    private int inputConstant;
    private Operator op;
    private Result result;
    private int resultConstant;
    private boolean global;

    public Variable(int valId, int valId1, int valId2,
                    int inputConstant, Operator op, Result result,
                    int resultConstant, boolean global, String deviceId, String name) {

        super(valId,deviceId, name);
        this.valId1 = valId1;
        this.valId2 = valId2;
        this.inputConstant = inputConstant;
        this.op = op;
        this.result = result;
        this.resultConstant = resultConstant;
        this.global = global;
    }

    public int getValId1() {
        return valId1;
    }

    public void setValId1(int valId1) {
        this.valId1 = valId1;
    }

    public int getValId2() {
        return valId2;
    }

    public void setValId2(int valId2) {
        this.valId2 = valId2;
    }

    public int getInputConstant() {
        return inputConstant;
    }

    public void setInputConstant(int inputConstant) {
        this.inputConstant = inputConstant;
    }

    public Operator getOp() {
        return op;
    }

    public void setOp(Operator op) {
        this.op = op;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getResultConstant() {
        return resultConstant;
    }

    public void setResultConstant(int resultConstant) {
        this.resultConstant = resultConstant;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "valId1=" + valId1 +
                ", valId2=" + valId2 +
                ", inputConstant=" + inputConstant +
                ", op=" + op +
                ", result=" + result +
                ", resultConstant=" + resultConstant +
                ", global=" + global +
                '}';
    }

    public List<String> toArgList(){
        List<String> argList = new ArrayList<>();
        argList.add(getValId()+"");
        argList.add(getValId1()+"");
        argList.add(getValId2()+"");
        argList.add(getInputConstant()+"");
        argList.add(getOp().ordinal()+"");
        argList.add(getResult().ordinal()+"");
        argList.add(getResultConstant()+"");
        argList.add(isGlobal()?"1":"0");
        return argList;
    }
}
