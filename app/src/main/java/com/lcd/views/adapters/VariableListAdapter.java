package com.lcd.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcd.models.Variable;

import java.util.Map;

import io.particle.android.sdk.cloud.ParticleDevice;
import lcd.particle.R;

/**
 * Created by matia on 19-Jun-17.
 */

public class VariableListAdapter extends AbstractPagedArrayAdapter<Variable> {

    @Override
    protected View fillView(Variable item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.variable_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.variableName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.variableDeviceName);
        device.append(item.getDeviceId());

        TextView valId1 = (TextView) view.findViewById(R.id.variableValId1);
        valId1.append(item.getValId1()+"");

        TextView valId2 = (TextView) view.findViewById(R.id.variableValId2);
        valId2.append(item.getValId2()+"");

        TextView inputConstant = (TextView) view.findViewById(R.id.variableInputConstant);
        inputConstant.append(item.getInputConstant()+"");

        TextView operator = (TextView) view.findViewById(R.id.variableOperator);
        operator.append(item.getOp().toString());

        TextView result = (TextView) view.findViewById(R.id.variableResult);
        result.append(item.getResult().toString());

        TextView resultConstant = (TextView) view.findViewById(R.id.variableResultConstant);
        resultConstant.append(item.getResultConstant()+"");

        TextView global = (TextView) view.findViewById(R.id.variableGlobal);
        global.append(item.isGlobal()+"");

        return view;
    }
}
