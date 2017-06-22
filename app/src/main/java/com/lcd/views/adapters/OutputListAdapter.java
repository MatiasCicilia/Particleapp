package com.lcd.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcd.models.OutputConnection;
import com.lcd.models.Variable;

import lcd.particle.R;

/**
 * Created by Ignacio on 6/21/2017.
 */


public class OutputListAdapter extends AbstractPagedArrayAdapter<OutputConnection> {

    @Override
    protected View fillView(OutputConnection item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.output_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.outputName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.outputDeviceName);
        device.append(item.getDeviceId());

        TextView valId = (TextView) view.findViewById(R.id.outputValId);
        valId.append(item.getValId()+"");

        TextView type = (TextView) view.findViewById(R.id.outputType);
        type.append(item.getOutputType().toString());

        TextView outputId = (TextView) view.findViewById(R.id.outputId);
        outputId.append(item.getOutputId()+"");
        return view;
    }
}