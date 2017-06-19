package com.lcd.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.particle.android.sdk.cloud.ParticleDevice;
import lcd.particle.R;

/**
 * Created by matias on 16/06/17.
 */

public class DeviceListAdapter extends AbstractPagedArrayAdapter<ParticleDevice> {

    @Override
    protected View fillView(ParticleDevice item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.device_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.deviceName);
        name.append(" " + item.getName());

        TextView description = (TextView) view.findViewById(R.id.deviceDescription);
        description.append(" " + item.getID());

        TextView status = (TextView) view.findViewById(R.id.deviceStatus);
        status.append(" " + item.getStatus());

        return view;
    }
}
