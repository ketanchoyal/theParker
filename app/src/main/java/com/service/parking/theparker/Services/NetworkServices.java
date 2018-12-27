package com.service.parking.theparker.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.parking.theparker.Model.ProfileModel;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.View.SnackbarWrapper;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class NetworkServices {

    public static class ProfileData {

        private String Name = "";
        private String email = "";
        private Context con;

        private EditText email_et;
        private EditText name_et;
        private EditText mobile_et;

        @NonNull
        public static ProfileData init(@NonNull String Name, @NonNull String email, @NonNull Context con) {
            return new ProfileData(Name, email, con);
        }

        public static ProfileData init(@NonNull EditText name_et, @NonNull EditText email_et, @NonNull EditText mobile_et, @NonNull Context con) {
            return new ProfileData(name_et, email_et, mobile_et, con);
        }

        ProfileData(String Name, String email, Context con) {
            this.Name = Name;
            this.email = email;
            this.con = con;

            updateData();
        }

        ProfileData(EditText name_et, EditText email_et, EditText mobile_et, Context con) {
            this.name_et = name_et;
            this.email_et = email_et;
            this.mobile_et = mobile_et;
            this.con = con;

            setData();
        }

        static private SharedPreferences sh;
        static private String Mobile_no;
        static private DatabaseReference mProfileReference;

        public void updateData() {

            sh = con.getSharedPreferences("myinfo",MODE_PRIVATE);
            Mobile_no = sh.getString(Theparker.SP_Mobileno,"");

            mProfileReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");

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

        void setData() {
            sh = con.getSharedPreferences("myinfo",MODE_PRIVATE);
            Mobile_no = sh.getString(Theparker.SP_Mobileno,"");

            mProfileReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");

            mProfileReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ProfileModel profileModel = dataSnapshot.getValue(ProfileModel.class);

                    email_et.setText(profileModel.Email);
                    name_et.setText(profileModel.Name);
                    mobile_et.setText(profileModel.Mobile_no);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

}
