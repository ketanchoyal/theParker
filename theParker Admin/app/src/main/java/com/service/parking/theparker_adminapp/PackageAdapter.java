package com.service.parking.theparker_adminapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.*;
import static android.widget.Toast.LENGTH_LONG;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyViewHolder> {

    private List<PackageModel>  packagelist;
    private Context con;
    private Object packageStatus;
    private DatabaseReference ref;

    PackageAdapter(List<PackageModel> packagelist){ this.packagelist= packagelist; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        con=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_package,parent,false);
        ref=FirebaseDatabase.getInstance().getReference().child("packages");
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final PackageModel model = packagelist.get(i);

        Log.d("Firebase : ",model.package_name+" "+model.bikes_selected+" "+model.cars_selected +" "+model.price);

        myViewHolder.a_name.setText(model.package_name.toString());
        myViewHolder.a_bike.setText(model.bikes_selected.toString());
        myViewHolder.a_car.setText(model.cars_selected.toString());
        myViewHolder.a_price.setText(model.price.toString());

        final String childID = model.id.toString();

        if (model.status.toString().equals("1")) {
            myViewHolder.a_switch.setChecked(true);

        }
        else {
            myViewHolder.a_switch.setChecked(false);
        }


        myViewHolder.a_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    packageStatus = 1;
                    myViewHolder.a_active.setBackgroundColor(Color.GREEN);
                    myViewHolder.a_active.setText("Active");

                } else {
                    packageStatus = 0;
                    myViewHolder.a_active.setBackgroundColor(Color.DKGRAY);
                    myViewHolder.a_active.setText("InActive");
                }

                Map<String,Object> map = new HashMap<>();
                map.put(AdminApp.FB_package_status,packageStatus);
                ref.child(childID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(con,"Status changed Successfully ",LENGTH_LONG).show();
                    }
                });
            }
        });

        myViewHolder.a_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(childID).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(con,"Deleted Successfully",Toast.LENGTH_LONG).show();
                    }
                });
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return packagelist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView a_name,a_price,a_car,a_bike;
        Button a_active,a_delete;
        Switch a_switch;

        MyViewHolder(View view){
            super(view);

            a_name=view.findViewById(R.id.card_Package_name);
            a_price=view.findViewById(R.id.card_package_price);
            a_bike = view.findViewById(R.id.card_bike_number);
            a_car= view.findViewById(R.id.card_car_number);
            a_switch = view.findViewById(R.id.card_switch1);
            a_active = view.findViewById(R.id.card_active_button);
            a_delete = view.findViewById(R.id.card_delete_button);
        }
    }
}
