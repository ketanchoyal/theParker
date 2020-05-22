package com.service.parking.theparker.View;


import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.service.parking.theparker.Model.Packages;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class PackageBuyDialog {
    Activity dialogContext;
    AlertDialog alertDialog;
    int style;

//    Context con;
    Packages packageModel;
    boolean cancellable = false;
    Runnable funcRun;

//    public TextView dia_package_name;
//    public TextView dia_package_price;
//    public TextView dia_after_balance;
////    public Button dia_confirm_purchase;
//    public TextView dia_wallet_balance;

    public PackageBuyDialog(Packages packages, Activity activity, Runnable funcRun, int style) {
        this.dialogContext = activity;
        this.packageModel = packages;
        this.funcRun = funcRun;
        this.style = style;
    }

    public void showAlertDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(dialogContext);
        View v = dialogContext.getLayoutInflater().inflate(R.layout.package_buy_dialog, null);
        TextView dia_after_balance = v.findViewById(R.id.dia_after_balance);
        TextView dia_package_name = v.findViewById(R.id.dia_package_name);
        TextView dia_package_price = v.findViewById(R.id.dia_package_price);
        Button dia_confirm_purchase = v.findViewById(R.id.dia_confirm_Purchase_btn);
        TextView dia_wallet_balance = v.findViewById(R.id.dia_wallet_balance);

        int wallet_balance = Integer.parseInt(NetworkServices.userProfile.Balance);
        int package_price = Integer.parseInt(packageModel.getPrice());

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;

        dia_package_name.setText(packageModel.getPackage_name());
        dia_package_price.setText(packageModel.getPrice());
        dia_wallet_balance.setText(""+wallet_balance);

        if (wallet_balance > package_price || wallet_balance == package_price) {
            //function for purchasing package
            dia_confirm_purchase.setText("CONFIRM PURCHASE");
            dia_after_balance.setText("Balance after buying this Package: ₹" + (wallet_balance - package_price));
        } else if (wallet_balance < package_price) {
            dia_after_balance.setText("You need to add : ₹" + (package_price - wallet_balance) + " to buy this package");
//            dia_after_balance.setTextColor(ContextCompat.getColor(con,R.color.colorRed));
            dia_confirm_purchase.setText("Add Balance");

            Runnable runnable = () -> {
//                Fragment fragment = new Fragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.wallet_fragment, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            };

            Toasty.error(dialogContext, "You have insufficient wallet balance", Toast.LENGTH_LONG).show();
        }

        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
        alertDialog.show();

        dia_confirm_purchase.setOnClickListener(v1 -> {

            if(dia_confirm_purchase.getText() == "CONFIRM PURCHASE") {
                funcRun.run();
                Toasty.info(dialogContext, "Buy package Clicked", Toast.LENGTH_LONG).show();
                closeSpinerDialog();
            } else {
                Toasty.info(dialogContext, "You have insufficient wallet balance", Toast.LENGTH_LONG).show();
                closeSpinerDialog();
            }

        });

    }

    public void closeSpinerDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }


}