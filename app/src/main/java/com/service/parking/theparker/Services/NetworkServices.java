package com.service.parking.theparker.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.service.parking.theparker.Controller.Adapters.PackageAdapter;
import com.service.parking.theparker.Model.PackageModel;
import com.service.parking.theparker.Model.ProfileModel;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.Utils.Constants;
import com.service.parking.theparker.View.SnackbarWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class NetworkServices {
    static private DatabaseReference REF = FirebaseDatabase.getInstance().getReference();

    public static class ProfileData {

        static DatabaseReference mProfileReference = REF.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile");

        public static void updateData(String Name, String email, Context con) {

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

        public static void setData(EditText name_et, EditText email_et, EditText mobile_et, Context con) {

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

    public static class Packages {
        static DatabaseReference mPackageRef = REF.child("packages");

        public static void getPackages(List<PackageModel> list, PackageAdapter adapter) {
            mPackageRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();

                    Log.d("ID : ",dataSnapshot.getKey());

                    PackageModel cm = new PackageModel(data.get(Constants.FB_package_name),data.get(Constants.FB_no_of_cars),data.get(Constants.FB_no_of_bike),
                            data.get(Constants.FB_package_price),data.get(Constants.FB_package_status),dataSnapshot.getKey());
                    list.add(cm);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
                {
//                    Toast.makeText(getApplicationContext(),""+dataSnapshot,Toast.LENGTH_LONG).show();

                    String keyID =dataSnapshot.getKey();
                    for(int i = 0;i<=list.size()-1;i++)
                    {
                        PackageModel object = list.get(i);
                        if(object.getId().equals(keyID))
                        {
                            list.remove(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
