package com.lcd.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import io.particle.android.sdk.cloud.ParticleDevice;
import lcd.particle.R;

/**
 * Created by matia on 19-Jun-17.
 */

public class VariableListAdapter extends AbstractPagedArrayAdapter {

    @Override
    protected View fillView(Object item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.variable_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.deviceName);
        name.append(" " + item);

        TextView variableType = (TextView) view.findViewById(R.id.deviceDescription);
        variableType.append(" " + item);

        TextView variableStatus = (TextView) view.findViewById(R.id.deviceStatus);
        variableStatus.append(" hardcoded ");
        return null;
    }
}