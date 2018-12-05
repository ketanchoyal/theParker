package com.service.parking.theparker;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Theparker extends Application {

    static String Mobile_no;
    static String Person_name;
    private DatabaseReference mProfileReference;

    static String SP_Name = "PersonName";
    static String SP_Mobileno = "Mobile_no";
    static String SP_User_id = "user_id";

    private SharedPreferences sh;

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

            //mProfileReference = FirebaseDatabase.getInstance().getReference().child(Mobile_no).child("Profile");
        }

    }

    
}
