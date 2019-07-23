package com.service.parking.theparker_adminapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class E_walletAdapter extends RecyclerView.Adapter<E_walletAdapter.MyViewholder> {

    private List<E_WalletModel> wallet_data;
    private Context con;
    private DatabaseReference ref1;

    E_walletAdapter(List<E_WalletModel> wallet_data){ this.wallet_data= wallet_data; }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        con = viewGroup.getContext();
            View itemView1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_wallet_layout,viewGroup,false);
            ref1=FirebaseDatabase.getInstance().getReference().child("transition_data");
        return new MyViewholder(itemView1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder myAdapter, int i) {
        final E_WalletModel e_walletModel = wallet_data.get(i);

        Log.d("Data Mil gaya Hai : ",e_walletModel.card_wallet_date + " " + e_walletModel.card_wallet_package_name +
                " " + e_walletModel.card_wallet_rate);

        myAdapter.a_card_wallet_price.setText(e_walletModel.getCard_wallet_rate());
        myAdapter.a_card_wallet_package_name.setText(e_walletModel.getCard_wallet_package_name());
        myAdapter.a_card_wallet_date.setText(e_walletModel.getCard_wallet_date());
    }

    @Override
    public int getItemCount() {
        return wallet_data.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        TextView a_card_wallet_date,a_card_wallet_package_name,a_card_wallet_price;

        public MyViewholder(@NonNull View itemView) {
             super(itemView);

             a_card_wallet_date=itemView.findViewById(R.id.card_wallet_date);
             a_card_wallet_package_name = itemView.findViewById(R.id.card_wallet_package_name);
             a_card_wallet_price = itemView.findViewById(R.id.card_wallet_price);
        }
    }
}
