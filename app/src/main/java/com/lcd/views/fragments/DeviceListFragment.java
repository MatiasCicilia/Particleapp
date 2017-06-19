package com.lcd.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcd.views.adapters.DeviceListAdapter;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleCloud;
import io.particle.android.sdk.cloud.ParticleCloudException;
import io.particle.android.sdk.cloud.ParticleCloudSDK;
import io.particle.android.sdk.cloud.ParticleDevice;
import io.particle.android.sdk.utils.Async;
import lcd.particle.R;
import retrofit.http.Part;

/**
 * Created by matias on 16/06/17.
 */

public class DeviceListFragment extends Fragment {
    private DeviceListAdapter adapter;
    private List<ParticleDevice> devices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        setupListView(view);

        ParticleCloudSDK.init(getActivity());



        // Creo que esto se tiene que llamar async
        try {
            devices = ParticleCloudSDK.getCloud().getDevices();
        } catch (ParticleCloudException e) {
            e.printStackTrace();
        }



        return view;
    }



    private void setupListView(View view) {
        adapter = new DeviceListAdapter();
        adapter.setKeepOnAppending(false);
        adapter.setManager(getFragmentManager());
    }

    public void sendItemsToAdapter() throws ParticleCloudException {
        adapter.clearItems();
        adapter.notifyNewItems(devices);
    }
}
