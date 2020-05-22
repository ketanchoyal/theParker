package com.service.parking.theparker.Controller.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.service.parking.theparker.Controller.Adapters.SlotsAdapter;
import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.Model.ParkingBooking;
import com.service.parking.theparker.Model.Transaction;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;
import com.service.parking.theparker.Theparker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.segmentedbutton.SegmentedButton;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ParkingPinDetailActivity extends AppCompatActivity {

    LocationPin selectedPin;
    @BindView(R.id.detail_btn_close)
    ImageView detailCloseBtn;
    @BindView(R.id.details_seg_spot_details)
    SegmentedButton detailsSegSpotDetails;
    @BindView(R.id.details_seg_spot_features)
    SegmentedButton detailsSegSpotFeatures;
    @BindView(R.id.detail_seg_booking_slots)
    SegmentedButton detailSegBookingSlots;
    @BindView(R.id.segmentedButtonGroup)
    SegmentedButtonGroup segmentedButtonGroup;
    @BindView(R.id.prof_img)
    CircleImageView profImg;
    @BindView(R.id.detail_person_name)
    TextView detailPersonName;
    @BindView(R.id.call_img)
    CircleImageView callBtn;
    @BindView(R.id.detail_person_mobile_no)
    TextView detailPersonMobileNo;
    @BindView(R.id.spot_type)
    TextView spotType;
    @BindView(R.id.spot_price)
    TextView spotPrice;
    @BindView(R.id.spot_description)
    TextView spotDescription;
    @BindView(R.id.detail_nextPage)
    CircleButton spotDetail_next_Btn;
    @BindView(R.id.spot_detail_layout)
    RelativeLayout spotDetailLayout;
    @BindView(R.id.detail_check_space_covered)
    ImageView CoveredFeatureCheck;
    @BindView(R.id.detail_space_check_security)
    ImageView SecurityFeatureCheck;
    @BindView(R.id.detail_check_onsite)
    ImageView CheckOnsiteFeatureCheck;
    @BindView(R.id.detail_space_check_disabled)
    ImageView DisabledFeatureCheck;
    @BindView(R.id.deails_btn_feature_next)
    CircleButton feature_next_Btn;
    @BindView(R.id.features_layout)
    RelativeLayout featuresLayout;
    @BindView(R.id.deails_btn_booking_next)
    CircleButton booking_final_Btn;
    @BindView(R.id.booking_layout)
    RelativeLayout bookingLayout;
    @BindView(R.id.mobileNoLayout)
    RelativeLayout mobileNoLayout;
    @BindView(R.id.layer0)
    RelativeLayout layer0;
    @BindView(R.id.btnpicktoday)
    Button DatePickButtonToday;
    @BindView(R.id.btnpicktomorrow)
    Button DatePickButtonTomorrow;
    @BindView(R.id.btnpickdayaftertomorrow)
    Button DatePickButtonDayAfterTomorrow;
    @BindView(R.id.recy)
    RecyclerView SlotRecycleeView;

    SlotsAdapter slotsAdapter;
    List<Map<String, Object>> slotsData;

    public static TextView AmountToPay;
    public static RelativeLayout AmountToPayLayout;

    public static int Year, monthOfYear, dayOfMonth;
    public static String noOfSlotsToBeBooked;
    public static ParkingBooking parkingBooking = new ParkingBooking();
    public static Transaction parkingBookingTransation = new Transaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_pin_detail);
        ButterKnife.bind(this);
        Theparker.animateSlide(this);

        AmountToPay = findViewById(R.id.amountToPay);
        AmountToPayLayout = findViewById(R.id.amountPayLayout);

        init();

    }

    void init() {
        selectedPin = Theparker.selectedLocationPin;
        Map<String, Boolean> features = selectedPin.getFeatures();
        features(features);

        slotsData = new ArrayList<>();

//        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(this);
//        SlotRecycleeView.setLayoutManager(mLayoutmanager);
//        SlotRecycleeView.setItemAnimator(new DefaultItemAnimator());
//        SlotRecycleeView.setAdapter(slotsAdapter);

        NetworkServices.ProfileData.getProfileDataById(selectedPin.getBy(), detailPersonName);
        detailPersonMobileNo.setText(selectedPin.getMobile());
        spotDescription.setText(selectedPin.getDescription());
        spotPrice.setText("₹" + selectedPin.getPrice() + "/4 Hour");
        spotType.setText(selectedPin.getType());

        detailCloseBtn.setOnClickListener(v -> {
            Theparker.animateSlide(ParkingPinDetailActivity.this);
            finish();
        });

        spotDetail_next_Btn.setOnClickListener(v -> {
            segmentedButtonGroup.setPosition(1);
            showFeatures();
        });

        feature_next_Btn.setOnClickListener(v -> {
            segmentedButtonGroup.setPosition(2);
            showBookings();
        });

        segmentedButtonGroup.setOnClickedButtonListener(position -> {
            switch (position) {
                case 0:
                    showSpotDetail();
                    break;
                case 1:
                    showFeatures();
                    break;
                case 2:
                    showBookings();
                    break;
            }
        });

        DatePickButtonToday.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE);

            picBtnPressed(year,month,date);
        });

        DatePickButtonTomorrow.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE) + 1;

            picBtnPressed(year,month,date);
        });

        DatePickButtonDayAfterTomorrow.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE) + 2;

            picBtnPressed(year,month,date);
        });

        booking_final_Btn.setOnClickListener(v -> {
            NetworkServices.TransactionData.doTransaction(parkingBookingTransation,parkingBooking);
//            bookingLayout.setVisibility(View.INVISIBLE);
            AmountToPayLayout.setVisibility(View.INVISIBLE);
            Toasty.success(this,"Parking Booked Successfully").show();
        });

    }

    void picBtnPressed(int year, int month, int day) {
        Log.d("Here: ","Clicked");
        SlotRecycleeView.setVisibility(View.VISIBLE);
        Year = year;
        monthOfYear = month;
        dayOfMonth = day;

        slotsData.clear();

        slotsAdapter = new SlotsAdapter(slotsData);
        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(this);
        SlotRecycleeView.setLayoutManager(mLayoutmanager);
        SlotRecycleeView.setItemAnimator(new DefaultItemAnimator());
        SlotRecycleeView.setAdapter(slotsAdapter);
        NetworkServices.Booking.getSlotData(selectedPin, year, month, day, slotsData, slotsAdapter);
    }

    private void features(Map<String, Boolean> features) {

        Boolean coveredFeature = features.get("Covered");
        Boolean staffFeature = features.get("Onsite Staff");
        Boolean cameraFeature = features.get("Security Camera");
        Boolean disabledAccessFeature = features.get("Disabled Access");

        isFeatureAvailabel(coveredFeature, CoveredFeatureCheck);
        isFeatureAvailabel(cameraFeature, SecurityFeatureCheck);
        isFeatureAvailabel(staffFeature, CheckOnsiteFeatureCheck);
        isFeatureAvailabel(disabledAccessFeature, DisabledFeatureCheck);

    }

    private void isFeatureAvailabel(Boolean feature, ImageView imageView) {
        if (feature) {
            imageView.setImageResource(R.drawable.ic_check);
        } else {
            imageView.setImageResource(R.drawable.ic_close);
        }
    }

    private void showSpotDetail() {
        spotDetailLayout.setVisibility(View.VISIBLE);
        featuresLayout.setVisibility(View.INVISIBLE);
        bookingLayout.setVisibility(View.INVISIBLE);
        AmountToPayLayout.setVisibility(View.INVISIBLE);
    }

    private void showFeatures() {
        spotDetailLayout.setVisibility(View.INVISIBLE);
        featuresLayout.setVisibility(View.VISIBLE);
        bookingLayout.setVisibility(View.INVISIBLE);
        AmountToPayLayout.setVisibility(View.INVISIBLE);
    }

    private void showBookings() {
        spotDetailLayout.setVisibility(View.INVISIBLE);
        featuresLayout.setVisibility(View.INVISIBLE);
        bookingLayout.setVisibility(View.VISIBLE);
    }

}
