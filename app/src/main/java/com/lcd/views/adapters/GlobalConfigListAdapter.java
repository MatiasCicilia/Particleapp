package com.lcd.views.adapters;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lcd.controllers.PhotonController;
import com.lcd.models.ForeignVariable;

import java.io.IOException;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleEventVisibility;
import io.particle.android.sdk.utils.Async;
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

        final TextView valRemote = (TextView) view.findViewById(R.id.inputListening);
        valRemote.append(item.getRemoteValId()+"");

        final EditText value = (EditText) view.findViewById(R.id.globalValue);

        Button send = (Button) view.findViewById(R.id.globalButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, Object>() {
                    @Override
                    public Object callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                        ParticleCloudSDK.getCloud().publishEvent("remotevar",item.getRemoteValId()+","+value.getText().toString()+",", ParticleEventVisibility.PUBLIC,50);

                        return null;
                    }

                    @Override
                    public void onSuccess(@NonNull Object o) {
                    }

                    @Override
                    public void onFailure(@NonNull ParticleCloudException exception) {
                    }
                });
            }
        });
        return view;
    }
}
