package com.lcd.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lcd.models.ForeignVariable;

import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleEventVisibility;
import lcd.particle.R;

/**
 * Created by Ignacio on 6/22/17.
 */

public class GlobalConfigListAdapter extends AbstractPagedArrayAdapter<ForeignVariable> {
    @Override
    protected View fillView(final ForeignVariable item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.global_config_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.inputName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.inputDeviceName);
        device.append(item.getDeviceId());

        final TextView valId = (TextView) view.findViewById(R.id.inputValId);
        valId.append(item.getValId()+"");

        final EditText value = (EditText) view.findViewById(R.id.globalValue);

        Button send = (Button) view.findViewById(R.id.globalButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer val = Integer.valueOf(value.getText().toString());
                try {
                    ParticleCloudSDK
                            .getCloud()
                            .publishEvent("remotevar",item.getRemoteValId()+","+val+",", ParticleEventVisibility.PUBLIC,300);
                } catch (ParticleCloudException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
