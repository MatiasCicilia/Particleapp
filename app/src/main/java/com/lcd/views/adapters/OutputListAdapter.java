package com.lcd.views.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lcd.controllers.PhotonController;
import com.lcd.models.OutputConnection;
import com.lcd.models.Variable;

import lcd.particle.R;

/**
 * Created by Ignacio on 6/21/2017.
 */


public class OutputListAdapter extends AbstractPagedArrayAdapter<OutputConnection> {

    @Override
    protected View fillView(final OutputConnection item, ViewGroup parent) {
        View view = inflater.inflate(R.layout.output_list_item ,parent, false);

        TextView name = (TextView) view.findViewById(R.id.outputName);
        name.append(item.getName());

        TextView device = (TextView) view.findViewById(R.id.outputDeviceName);
        device.append(item.getDeviceId());

        TextView valId = (TextView) view.findViewById(R.id.outputValId);
        valId.append(item.getValId()+"");

        TextView type = (TextView) view.findViewById(R.id.outputType);
        type.append(item.getOutputType().toString());

        TextView outputId = (TextView) view.findViewById(R.id.outputId);
        outputId.append(item.getOutputId()+"");

        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                PhotonController.getInstance().getOutputConnections().remove(item);
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