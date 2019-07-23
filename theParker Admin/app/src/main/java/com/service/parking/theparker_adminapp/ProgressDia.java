package com.service.parking.theparker_adminapp;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProgressDia
{
    Context con;
    Dialog dia;
    TextView tv;
    public ProgressDia(Context con)
    {
        this.con=con;
        dia=new Dialog(con);
        dia.setCancelable(false);
        //dia.setContentView(R.layout.dialoug_progress);
        Window window = dia.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv=dia.findViewById(R.id.title);
    }

    public void setMessage(String msg)
    {
        tv.setText(msg);
    }

    public void dismiss()
    {
        dia.dismiss();
    }
    public void show()
    {
        dia.show();
    }
}
