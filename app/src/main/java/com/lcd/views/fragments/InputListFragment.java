package com.lcd.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lcd.views.adapters.DeviceListAdapter;

import java.io.IOException;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.utils.Async;
import lcd.particle.R;

/**
 * Created by matia on 19-Jun-17.
 */

public class InputListFragment extends Fragment {
    private DeviceListAdapter adapter;
    private List<ParticleDevice> devices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        setupListView(view);

        sendItemsToAdapter();

        return view;
    }



    private void setupListView(View view) {
        ListView lvDevice = (ListView) view.findViewById(R.id.lvDevice);
        adapter = new DeviceListAdapter();
        adapter.init(getActivity(), lvDevice);
        adapter.setKeepOnAppending(false);
        adapter.setManager(getFragmentManager());
        lvDevice.setAdapter(adapter);
    }

    public void sendItemsToAdapter() {
        Async.executeAsync(ParticleCloudSDK.getCloud(), new Async.ApiWork<ParticleCloud, List<ParticleDevice>>() {
            @Override
            public List<ParticleDevice> callApi(@NonNull ParticleCloud particleCloud) throws ParticleCloudException, IOException {
                return ParticleCloudSDK.getCloud().getDevices();
            }

            @Override
            public void onSuccess(@NonNull List<ParticleDevice> particleDevices) {
                devices = particleDevices;
                adapter.clearItems();
                adapter.notifyNewItems(devices);
            }

            @Override
            public void onFailure(@NonNull ParticleCloudException exception) {

            }
        });

    }
}
