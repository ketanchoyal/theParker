package com.service.parking.theparker.View;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.service.parking.theparker.Model.Packages;
import com.service.parking.theparker.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class PackageBuyDialog {
    Activity dialogContext;
    AlertDialog alertDialog;
    int style;
    public String wallet_balance="0";

    Context con;
    Packages packages;
    boolean cancellable = false;

    public TextView dia_package_name,dia_wallet_balance,dia_package_price,dia_balance_left;
    public Button dia_confirm_purchase;

    public PackageBuyDialog(Packages packages, ArrayList <String> dialogItem, String dialogTitle, Activity activity) {
        this.dialogContext = activity;
        this.packages = packages;
    }
    public PackageBuyDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle){
        this.dialogContext = activity;
    }
    public PackageBuyDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style){
        this.dialogContext = activity;
        this.style = style;
    }
    public PackageBuyDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style, String closeTitle){
        this.dialogContext = activity;
        this.style = style;

    }
    public void showAlertDialog(){
        AlertDialog.Builder adb = new AlertDialog.Builder(dialogContext);
        View v = dialogContext.getLayoutInflater().inflate(R.layout.package_buy_dialog,null);
        dia_balance_left = v.findViewById(R.id.dia_balance_left);
        dia_package_name = v.findViewById(R.id.dia_package_name);
        dia_package_price = v.findViewById(R.id.package_price);
        dia_confirm_purchase = v.findViewById(R.id.dia_confirm_Purchase_btn);

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;

        if(wallet_balance == packages.getPrice())
        {
            //function for purchasing package
        }
        if(wallet_balance != packages.getPrice()){
            Toasty.error(con,"You have inscufficient wallet balance", Toast.LENGTH_LONG).show();
        }
        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
    }
    private boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }


}