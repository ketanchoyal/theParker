package com.service.parking.theparker.Controller.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.service.parking.theparker.Controller.Activity.ParkingPinDetailActivity;
import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.Model.ParkingBooking;
import com.service.parking.theparker.Model.Transaction;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.SlotViewHolder> {

    private List<Map<String,Object>> slotsDataList;
    private LocationPin selectedPin;
    private List<RelativeLayout>relativeLayoutList = new ArrayList<>();
    private Context context;

    public SlotsAdapter(List<Map<String,Object>> slotsDataList) {
        this.slotsDataList = slotsDataList;
        selectedPin = Theparker.selectedLocationPin;
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parking_slots_layout,viewGroup,false);
        context = viewGroup.getContext();
        return new SlotViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder slotViewHolder, int i) {
        Map<String,Object> slot = slotsDataList.get(i);

        relativeLayoutList.add(slotViewHolder.relativeLayout);

        if (!relativeLayoutList.contains(slotViewHolder.relativeLayout)) {
            relativeLayoutList.add(slotViewHolder.relativeLayout);
        }

        slotViewHolder.relativeLayout.setOnClickListener(v -> {
            changeColor(slotViewHolder);
        });

        slotViewHolder.mTime.setText(slot.get("slot").toString());

        int totalSpots = Integer.parseInt(selectedPin.getNumberofspot());
        int spotsBooked = Integer.parseInt(slot.get("booked").toString());
        int spotsAvailable = totalSpots - spotsBooked;

        slotViewHolder.mBookedSlots.setText(""+spotsBooked+" Booked");
        slotViewHolder.mAvailableSlots.setText(""+spotsAvailable+" Available");

        slotViewHolder.mNoOfSlots.setText("0");

        slotViewHolder.mPlus.setOnClickListener(v -> {
            int a = Integer.parseInt(slotViewHolder.mNoOfSlots.getText().toString());
            if (spotsAvailable > a) {
                a = a + 1;
                slotViewHolder.mNoOfSlots.setText(""+a);
                changeColor(slotViewHolder);
                updateData(slotViewHolder,i);
                ParkingPinDetailActivity.noOfSlotsToBeBooked = "" + (Integer.parseInt(slot.get("booked").toString()) + Integer.parseInt(slotViewHolder.mNoOfSlots.getText().toString()));
            }
        });

        slotViewHolder.mMin.setOnClickListener(v -> {
            int a = Integer.parseInt(slotViewHolder.mNoOfSlots.getText().toString());
            if (a>0) {
                a--;
                slotViewHolder.mNoOfSlots.setText(""+a);
                changeColor(slotViewHolder);
                updateData(slotViewHolder,i);
                ParkingPinDetailActivity.noOfSlotsToBeBooked = "" + (Integer.parseInt(slot.get("booked").toString()) + Integer.parseInt(slotViewHolder.mNoOfSlots.getText().toString()));
            }
        });

        slotViewHolder.mBookButton.setOnClickListener(v -> {
            updateData(slotViewHolder,i);
            changeColor(slotViewHolder);
        });
    }

    void changeColor(SlotViewHolder slotViewHolder) {
        for(RelativeLayout relativeLayout : relativeLayoutList){
            relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
        }
        //The selected card is set to colorSelected
        slotViewHolder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGray));
    }

    void updateData(SlotViewHolder slotViewHolder, int i) {
        int amount = Integer.parseInt(selectedPin.getPrice()) * Integer.parseInt(slotViewHolder.mNoOfSlots.getText().toString());

        ParkingPinDetailActivity.AmountToPay.setText("" + amount + " /-");
        ParkingPinDetailActivity.AmountToPayLayout.setVisibility(View.VISIBLE);

        String slotNo = "slot"+ (i+1);

        ParkingBooking parkingBooking = ParkingPinDetailActivity.parkingBooking;
        parkingBooking.setSlotNo(slotNo);
        parkingBooking.setParkingArea(selectedPin.getArea());
        parkingBooking.setParkingId(selectedPin.getPinkey());
        parkingBooking.setSpotHost(selectedPin.getBy());
        parkingBooking.setBy(FirebaseAuth.getInstance().getUid());

        Transaction parkingBookingTransaction = ParkingPinDetailActivity.parkingBookingTransation;
        parkingBookingTransaction.setAmount(""+amount);
        parkingBookingTransaction.setBy(FirebaseAuth.getInstance().getUid());
        parkingBookingTransaction.setForr(selectedPin.getBy());
        parkingBookingTransaction.setId(selectedPin.getPinkey());
        parkingBookingTransaction.setOf("Parking");

    }

    @Override
    public int getItemCount() {
        return slotsDataList.size();
    }

    class SlotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.plus)
        ImageView mPlus;

        @BindView(R.id.min)
        ImageView mMin;

        @BindView(R.id.slots)
        TextView mNoOfSlots;

        @BindView(R.id.time)
        TextView mTime;

        @BindView(R.id.booked)
        TextView mBookedSlots;

        @BindView(R.id.available)
        TextView mAvailableSlots;

        @BindView(R.id.book)
        ImageView mBookButton;

        @BindView(R.id.slot_layout_view)
        RelativeLayout relativeLayout;

        SlotViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
