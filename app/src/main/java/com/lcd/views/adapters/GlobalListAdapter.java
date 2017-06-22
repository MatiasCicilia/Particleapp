package com.lcd.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcd.models.ForeignVariable;
import com.lcd.models.InputConnection;

import lcd.particle.R;

/**
 * Created by Ignacio on 6/21/2017.
 */

public class GlobalListAdapter extends AbstractPagedArrayAdapter<ForeignVariable> {
    @Override
    protected View fillView(ForeignVariable item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.global_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.inputName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.inputDeviceName);
        device.append(item.getDeviceId());

        TextView valId = (TextView) view.findViewById(R.id.inputValId);
        valId.append(item.getValId()+"");
        return view;
    }
}
