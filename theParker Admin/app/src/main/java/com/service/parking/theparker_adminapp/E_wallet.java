package com.service.parking.theparker_adminapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.service.parking.theparker_adminapp.Main3Activity.adapter;

public class E_wallet extends AppCompatActivity {

    TextView wallet_balance;

    FirebaseDatabase db1;
    DatabaseReference ref1;

    E_walletAdapter adapter1;
    List<E_WalletModel> list1;
    RecyclerView recy1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_wallet);
        recy1 = findViewById(R.id.card_wallet_recy);
        wallet_balance = findViewById(R.id.wallet_balance);
        list1 = new ArrayList<>();
        adapter1 = new E_walletAdapter(list1);
        wallet_balance.setText("50");


        db1 =FirebaseDatabase.getInstance();
        ref1 = db1.getReference();

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(E_wallet.this);
        recy1.setLayoutManager(mLayoutmanager);
        recy1.setItemAnimator(new DefaultItemAnimator());
        recy1.setAdapter(adapter1);

        getDataFromDatabase();


    }

    public void getDataFromDatabase()
    {
        ref1.child("transition_data").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.d("Snapshot",dataSnapshot.getKey());
                Log.d("Data : ",dataSnapshot.toString());

                HashMap data = (HashMap) dataSnapshot.getValue();

                Log.d("Data : ",data.get(AdminApp.FB_wallet_date).toString());

                E_WalletModel cm = new E_WalletModel();
                cm.setCard_wallet_date(data.get(AdminApp.FB_wallet_date).toString());
                cm.setCard_wallet_package_name(data.get(AdminApp.Fb_wallet_package_name).toString());
                cm.setCard_wallet_rate(data.get(AdminApp.FB_wallet_price).toString());
                list1.add(cm);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {


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
