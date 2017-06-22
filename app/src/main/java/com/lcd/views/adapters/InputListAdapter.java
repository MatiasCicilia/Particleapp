package com.lcd.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcd.models.InputConnection;

import lcd.particle.R;

public class InputListAdapter extends AbstractPagedArrayAdapter<InputConnection> {
    @Override
    protected View fillView(InputConnection item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.input_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.inputName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.inputDeviceName);
        device.append(item.getDeviceId());

        TextView valId = (TextView) view.findViewById(R.id.inputValId);
        valId.append(item.getValId()+"");

        TextView type = (TextView) view.findViewById(R.id.inputType);
        type.append(item.getInput().toString());

        TextView inputId = (TextView) view.findViewById(R.id.inputId);
        inputId.append(item.getInputId()+"");
        return view;
    }
}
