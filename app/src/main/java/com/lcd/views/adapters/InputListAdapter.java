package com.lcd.views.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lcd.controllers.PhotonController;
import com.lcd.models.InputConnection;

import lcd.particle.R;

public class InputListAdapter extends AbstractPagedArrayAdapter<InputConnection> {
    @Override
    protected View fillView(final InputConnection item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.input_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.inputName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.inputDeviceName);
        device.append(item.getDeviceId());

        TextView valId = (TextView) view.findViewById(R.id.inputValId);
        valId.append(item.getValId()+"");

        TextView type = (TextView) view.findViewById(R.id.inputType);
        type.append(item.getInput().toString());

        TextView inputId = (TextView) view.findViewById(R.id.inputId);
        inputId.append(item.getInputId()+"");

        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                PhotonController.getInstance().getInputConnections().remove(item);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //context.onBackPressed();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }

        });

        return view;
    }
}
