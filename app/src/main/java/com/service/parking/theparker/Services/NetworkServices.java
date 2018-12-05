package com.service.parking.theparker.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.View.SnackbarWrapper;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class NetworkServices {

    public static class ProfileData {

        private final String Name;
        private final String email;
        private final Context con;

        @NonNull
        public static ProfileData init(@NonNull String Name, @NonNull String email, @NonNull Context con) {
            return new ProfileData(Name, email, con);
        }

        ProfileData(String Name, String email, Context con) {
            this.Name = Name;
            this.email = email;
            this.con = con;

            updateData();
        }

        static private SharedPreferences sh;
        static private String Mobile_no;
        static private DatabaseReference mProfileReference;

        void updateData() {

            sh = con.getSharedPreferences("myinfo",MODE_PRIVATE);
            Mobile_no = sh.getString(Theparker.SP_Mobileno,"");

            mProfileReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Mobile_no).child("Profile");

            final Map<String,Object> UserdataMap =new HashMap<>();
            UserdataMap.put("Name",Name);
            UserdataMap.put("Email",email);

            mProfileReference.updateChildren(UserdataMap).addOnCompleteListener(v -> {

                if (v.isSuccessful()) {
                    SnackbarWrapper.make(con, "Sucessfully Updated Profile");
                }
                else {
                    SnackbarWrapper.make(con, "Please try again!");
                }
            });

        }


    }

}
