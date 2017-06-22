package com.lcd.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lcd.controllers.PhotonController;
import com.lcd.views.adapters.GlobalListAdapter;
import com.lcd.views.adapters.OutputListAdapter;

import lcd.particle.R;

/**
 * Created by Ignacio on 6/21/2017.
 */

public class GlobalListFragment extends Fragment {
    private GlobalListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setupListView(view);

        adapter.notifyNewItems(PhotonController.getInstance().getForeignVariables());

        return view;
    }


    private void setupListView(View view) {
        ListView lvVariable = (ListView) view.findViewById(R.id.lvList);
        adapter = new GlobalListAdapter();
        adapter.init(getActivity(), lvVariable);
        adapter.setKeepOnAppending(false);
        adapter.setManager(getFragmentManager());
        lvVariable.setAdapter(adapter);
    }
}