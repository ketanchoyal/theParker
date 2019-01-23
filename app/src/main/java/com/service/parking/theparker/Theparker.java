package com.service.parking.theparker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.service.parking.theparker.Controller.Activity.LoginActivity;
import com.service.parking.theparker.View.ActivityAnimator;

public class Theparker extends Application {

    public static String Mobile_no;
    public static String Person_name;

    public static String SP_Name = "PersonName";
    public static String SP_Mobileno = "Mobile_no";
    public static String SP_User_id = "user_id";

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sh=getSharedPreferences("myinfo",MODE_PRIVATE);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {
            Intent LoginIntent = new Intent(this,LoginActivity.class);
            startActivity(LoginIntent);

        }
        else  {

            Mobile_no = sh.getString(SP_Mobileno,"");
            Person_name = sh.getString(SP_Name,"");

        }

    }

    public static void animate(Activity activity) {
        try {
            ActivityAnimator.fadeAnimation(activity);
        } catch (Exception ignore) {}
    }
}
