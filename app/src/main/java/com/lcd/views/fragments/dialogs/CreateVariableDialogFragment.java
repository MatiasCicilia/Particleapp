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
import com.lcd.models.enums.Operator;
import com.lcd.models.enums.Result;
import com.lcd.util.DpiUtils;

import java.util.ArrayList;
import java.util.List;

import io.particle.android.sdk.cloud.ParticleDevice;
import lcd.particle.R;

/**
 * Created by Matias Cicilia on 20-Jun-17.
 */

public class CreateVariableDialogFragment extends DialogFragment {
    private final static String TAG = CreateVariableDialogFragment.class.getSimpleName();
    private boolean isGlobal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_variable, container, false);

        final EditText variableName = (EditText) view.findViewById(R.id.variableNameText);

        final Spinner deviceSpinner = (Spinner) view.findViewById(R.id.variableDeviceSpinner);
        renderSpinner(devicesToStringList(PhotonController.getInstance().getDevices()), deviceSpinner);

        final Spinner valId1Spinner = (Spinner) view.findViewById(R.id.variableValId1Spinner);
        final Spinner valId2Spinner = (Spinner) view.findViewById(R.id.variableValId2Spinner);

        final EditText inputConstant = (EditText) view.findViewById(R.id.variableInputConstantText);
        final EditText resultConstant = (EditText) view.findViewById(R.id.variableResultConstantText);

        final Spinner operatorSpinner = (Spinner) view.findViewById(R.id.variableOperatorSpinner);
        renderSpinner(Operator.getListValues(), operatorSpinner);

        final Spinner resultSpinner = (Spinner) view.findViewById(R.id.variableResultSpinner);
        renderSpinner(Result.getListValues(), resultSpinner);

        /* Let the spinners know which device was picked */
        deviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String device = (String) deviceSpinner.getItemAtPosition(position);
                renderSpinner(variableToStringList(PhotonController.getInstance().getPossibleVariables(device)), valId1Spinner);
                renderSpinner(variableToStringList(PhotonController.getInstance().getPossibleVariables(device)), valId2Spinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Switch globalSwitch = (Switch) view.findViewById(R.id.globalVariableSwitch);
        Button sendButton = (Button) view.findViewById(R.id.variableSendButton);
        Button cancelButton = (Button) view.findViewById(R.id.variableCancelButton);

        globalSwitch.setChecked(false);
        globalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGlobal = isChecked;
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotonController.getInstance()
                        .createVariable(Integer.parseInt((String)valId1Spinner.getSelectedItem()),
                        Integer.parseInt((String)valId2Spinner.getSelectedItem()),
                        Integer.parseInt(inputConstant.getText().toString()),
                        Operator.valueOf((String)operatorSpinner.getSelectedItem()),
                        Result.valueOf((String)resultSpinner.getSelectedItem()),
                        Integer.parseInt(resultConstant.getText().toString()),
                        isGlobal,
                        (String) deviceSpinner.getSelectedItem(),
                        variableName.getText().toString());
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

    private List<String> variableToStringList(List<AbstractVariable> list) {
        List<String> aux = new ArrayList<>();
        for (AbstractVariable a: list) {
            aux.add(Integer.toString(a.getValId()));
        }
        aux.add("-2");
        aux.add("-1");
        return aux;
    }


}
