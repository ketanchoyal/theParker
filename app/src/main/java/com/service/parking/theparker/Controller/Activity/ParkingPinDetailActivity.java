package com.service.parking.theparker.Controller.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;
import com.service.parking.theparker.Theparker;

import java.util.Map;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.segmentedbutton.SegmentedButton;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_pin_detail);
        ButterKnife.bind(this);
        Theparker.animateSlide(this);
        init();

    }

    void init() {
        selectedPin = Theparker.selectedLocationPin;
        Map<String, Boolean> features = selectedPin.getFeatures();
        features(features);

        NetworkServices.ProfileData.getProfileDataById(selectedPin.getBy(),detailPersonName);
        detailPersonMobileNo.setText(selectedPin.getMobile());
        spotDescription.setText(selectedPin.getDescription());
        spotPrice.setText("₹"+selectedPin.getPrice()+"/4 Hour");
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
    }

    private void features(Map<String, Boolean> features) {

        Boolean coveredFeature = features.get("Covered");
        Boolean staffFeature = features.get("Onsite Staff");
        Boolean cameraFeature = features.get("Security Camera");
        Boolean disabledAccessFeature = features.get("Disabled Access");

        isFeatureAvailabel(coveredFeature, CoveredFeatureCheck);
        isFeatureAvailabel(cameraFeature, SecurityFeatureCheck);
        isFeatureAvailabel(staffFeature,CheckOnsiteFeatureCheck);
        isFeatureAvailabel(disabledAccessFeature,DisabledFeatureCheck);

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
    }

    private void showFeatures() {
        spotDetailLayout.setVisibility(View.INVISIBLE);
        featuresLayout.setVisibility(View.VISIBLE);
        bookingLayout.setVisibility(View.INVISIBLE);
    }

    private void showBookings() {
        spotDetailLayout.setVisibility(View.INVISIBLE);
        featuresLayout.setVisibility(View.INVISIBLE);
        bookingLayout.setVisibility(View.VISIBLE);
    }

}
