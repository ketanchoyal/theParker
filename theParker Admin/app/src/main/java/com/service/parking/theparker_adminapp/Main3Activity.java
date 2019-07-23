package com.service.parking.theparker_adminapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {

    Switch card_switch;
    EditText package_name,package_price;
    TextView card_name,card_price,card_car,card_bike;
    static Context con;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    RecyclerView recy;
    static PackageAdapter adapter;
    List<PackageModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        con=getApplicationContext();
        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference().child("packages");
        recy = findViewById(R.id.recy);
        list= new ArrayList<>();
        adapter = new PackageAdapter(list);

        con = getApplicationContext();
        card_bike = findViewById(R.id.card_bike_number);
        card_car = findViewById(R.id.card_car_number);
        card_name = findViewById(R.id.card_Package_name);
        card_price = findViewById(R.id.card_package_price);
        package_name = findViewById(R.id.package_name);
        package_price = findViewById(R.id.package_price);
        card_switch = findViewById(R.id.card_switch1);

        list = new ArrayList<>();
        adapter= new PackageAdapter(list);
        con=getApplicationContext();

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(Main3Activity.this);
        recy.setLayoutManager(mLayoutmanager);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter);

        getDataFromDatabase();

        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                CustomDialogClass cdd = new CustomDialogClass(Main3Activity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                cdd.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(Main3Activity.this, Main2Activity.class));
            finish();
        }
    }

    public void getDataFromDatabase(){
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

//                Map<String,String> data = (Map<String, String>) dataSnapshot.getValue();
//                PackageModel cm = new PackageModel(data.get(AdminApp.FB_package_name),data.get(AdminApp.FB_no_of_cars)
//                        ,data.get(AdminApp.FB_no_of_bike),data.get(AdminApp.FB_package_price),data.get(AdminApp.FB_package_status));

                Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();

                Log.d("ID : ",dataSnapshot.getKey());

                PackageModel cm = new PackageModel(data.get(AdminApp.FB_package_name),data.get(AdminApp.FB_no_of_cars),data.get(AdminApp.FB_no_of_bike),
                        data.get(AdminApp.FB_package_price),data.get(AdminApp.FB_package_status),dataSnapshot.getKey());
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
              // Toast.makeText(getApplicationContext(),""+dataSnapshot,Toast.LENGTH_LONG).show();

                String keyID =dataSnapshot.getKey();
                for(int i = 0;i<=list.size()-1;i++)
                {
                    PackageModel object = list.get(i);
                    if(object.id.equals(keyID))
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
