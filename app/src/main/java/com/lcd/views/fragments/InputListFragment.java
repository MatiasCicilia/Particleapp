package com.lcd.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lcd.controllers.PhotonController;
import com.lcd.views.adapters.DeviceListAdapter;
import com.lcd.views.adapters.InputListAdapter;
import com.lcd.views.adapters.VariableListAdapter;

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
    private InputListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setupListView(view);

        adapter.notifyNewItems(PhotonController.getInstance().getInputConnections());

        return view;
    }


    private void setupListView(View view) {
        ListView lvVariable = (ListView) view.findViewById(R.id.lvList);
        adapter = new InputListAdapter();
        adapter.init(getActivity(), lvVariable);
        adapter.setKeepOnAppending(false);
        adapter.setManager(getFragmentManager());
        lvVariable.setAdapter(adapter);
    }
}

