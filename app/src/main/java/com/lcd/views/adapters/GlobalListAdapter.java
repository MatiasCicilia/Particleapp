package com.lcd.views.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lcd.controllers.PhotonController;
import com.lcd.models.ForeignVariable;
import com.lcd.models.InputConnection;

import lcd.particle.R;

/**
 * Created by Ignacio on 6/21/2017.
 */

public class GlobalListAdapter extends AbstractPagedArrayAdapter<ForeignVariable> {
    @Override
    protected View fillView(final ForeignVariable item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.global_list_item ,parent, false);
        Log.d("Global List", "********************");

        TextView name = (TextView) view.findViewById(R.id.inputName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.inputDeviceName);
        device.append(item.getDeviceId() + "");

        TextView valId = (TextView) view.findViewById(R.id.inputValId);
        valId.append(item.getValId() + "");

        TextView listening = (TextView) view.findViewById(R.id.inputListening);
        listening.append(item.getRemoteValId() + "");

        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                PhotonController.getInstance().getForeignVariables().remove(item);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do nothing
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
