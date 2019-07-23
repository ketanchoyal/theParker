package com.service.parking.theparker_adminapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class Custom_dialog extends DialogFragment {

    LayoutInflater inflater;
    View view;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.custom_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();

    }
}
