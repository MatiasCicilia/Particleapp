package com.lcd.views.fragments.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.lcd.controllers.PhotonController;
import com.lcd.models.AbstractVariable;
import com.lcd.models.Variable;
import com.lcd.models.enums.Operator;
import com.lcd.models.enums.Result;
import com.lcd.util.DpiUtils;

import java.util.ArrayList;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleDevice;
import lcd.particle.R;

/**
 * Created by Ignacio on 6/21/2017.
 */

public class CreateGlobalDialogFragment  extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_global, container, false);

        final EditText variableName = (EditText) view.findViewById(R.id.variableNameText);

        final Spinner deviceSpinner = (Spinner) view.findViewById(R.id.variableDeviceSpinner);
        renderSpinner(devicesToStringList(PhotonController.getInstance().getDevices()), deviceSpinner);

        final Spinner valId1Spinner = (Spinner) view.findViewById(R.id.variableValId1Spinner);
        renderSpinner(variableToStringList(PhotonController.getInstance().getGlobalVariables()), valId1Spinner);

        Button sendButton = (Button) view.findViewById(R.id.variableSendButton);
        Button cancelButton = (Button) view.findViewById(R.id.variableCancelButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotonController.getInstance()
                        .createForeignVariable(
                                (String) deviceSpinner.getSelectedItem(),
                                variableName.getText().toString(),
                                Integer.parseInt((String)valId1Spinner.getSelectedItem()));
                getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        Window window = getDialog().getWindow();
        if(window != null) {
            DisplayMetrics dm = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DpiUtils.toPixels(300,dm);
            params.height = DpiUtils.toPixels(500,dm);
            window.setAttributes(params);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if(window != null) {
            DisplayMetrics dm = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DpiUtils.toPixels(300,dm);
            params.height = DpiUtils.toPixels(500,dm);
            window.setAttributes(params);
        }
    }

    public void renderSpinner(List<String> names, Spinner spinner) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private List<String> devicesToStringList(List<ParticleDevice> list) {
        List<String> aux = new ArrayList<>();
        for (ParticleDevice d: list) {
            aux.add(d.getName());
        }
        return aux;
    }

    private List<String> variableToStringList(List<Variable> list) {
        List<String> aux = new ArrayList<>();
        for (Variable a: list) {
            aux.add(Integer.toString(a.getValId()));
        }
        aux.add("-1");
        return aux;
    }
}