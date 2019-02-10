package com.service.parking.theparker.Controller.Activity.OfferLocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.Utils.LocationConstants;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class AreaAndAddressActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_name)
    TextView mActionBarName;

    @BindView(R.id.back_btn)
    CircleImageView mBackBtn;

    @BindView(R.id.next_btn)
    CircleButton mNextBtn;

    @BindView(R.id.area_edit_text)
    ExtendedEditText mArea;

    @BindView(R.id.address_edit_text)
    ExtendedEditText mAddress;

    @BindView(R.id.mobileno_edit_text)
    ExtendedEditText mMobileNo;

    @BindView(R.id.pincode_edit_text)
    ExtendedEditText mPinCode;

    private String area;
    private String address;
    private String pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_and_address);
        ButterKnife.bind(this);
        Theparker.animate(this);

        area = getIntent().getStringExtra(LocationConstants.area);
        address = getIntent().getStringExtra(LocationConstants.address);
        pincode = getIntent().getStringExtra(LocationConstants.pincode);

        mArea.setText(area);
        mAddress.setText(address);
        mPinCode.setText(pincode);

        mActionBarName.setText("Area and Address");

        mBackBtn.setOnClickListener(v -> {
            finish();
            Theparker.animate(this);
        });

        mNextBtn.setOnClickListener(v -> {
            startActivity(new Intent(AreaAndAddressActivity.this, ParkingDetailsActivity.class));
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }
}
