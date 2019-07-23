package com.service.parking.theparker_adminapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class CustomDialogClass extends Dialog {
    private Activity c;
    public Dialog d;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    Button create_package;
    Spinner spinner_car,spinner_bike;
    private Object car_selected,bike_selected;
    EditText package_name,package_price;
    Switch card_switch;
    CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_layout);
        db=FirebaseDatabase.getInstance();
        ref=db.getReference();

        spinner_car = findViewById(R.id.Spinner_car);
        spinner_bike = findViewById(R.id.Spinner_bike);
        create_package = findViewById(R.id.create_package);
        package_name = findViewById(R.id.package_name);
        package_price = findViewById(R.id.package_price);
        //    card_active_button = (Button) findViewById(R.id.card_active_button);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(c,R.array.car_array,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_car.setAdapter(adapter);
        spinner_car.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_selected = parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(c,R.array.array_bike,android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_bike.setAdapter(adapter1);
        spinner_bike.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bike_selected=parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        create_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePackageDetail();
                Intent i = new Intent(c,Main3Activity.class);
                c.startActivity(i);
                c.finish();
            }
        });

    }

    private void savePackageDetail(){
        String name= package_name.getText().toString();
        String price=package_price.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put(AdminApp.FB_no_of_cars,car_selected);
        map.put(AdminApp.FB_no_of_bike,bike_selected);
        map.put(AdminApp.FB_package_name,name);
        map.put(AdminApp.FB_package_price,price);
        map.put(AdminApp.FB_package_status,1);
        ref.child("packages").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(c,"Added Successfully ",LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(c,"Nai hua",LENGTH_LONG).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(c,"Phir se karo",LENGTH_LONG).show();
            }
        });
    }
}