package com.lcd.views.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lcd.controllers.PhotonController;
import com.lcd.models.Variable;

import java.util.List;
import java.util.Map;

import io.particle.android.sdk.cloud.ParticleDevice;
import lcd.particle.R;

/**
 * Created by Matias Cicilia on 19-Jun-17.
 */

public class VariableListAdapter extends AbstractPagedArrayAdapter<Variable> {

    @Override
    protected View fillView(final Variable item, ViewGroup parent) {
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

        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                PhotonController.getInstance().getVariables().remove(item);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //context.onBackPressed();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }

        });

        return view;
    }
}
