package com.service.parking.theparker.Controller.Activity.OfferLocation;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.Utils.LocationConstants;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
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
            onBackPressed();
            Theparker.animate(this);
        });

        mNextBtn.setOnClickListener(v -> {
            String mobileno = mMobileNo.getEditableText().toString();
            area = mArea.getEditableText().toString();
            address = mAddress.getEditableText().toString();
            pincode = mPinCode.getEditableText().toString();

            if (mobileno.isEmpty() || area.isEmpty() || address.isEmpty() || pincode.isEmpty()) {
                Toasty.error(this,"Please fill the information correctly").show();
            } else {
                LocationPin locationPin = Theparker.currentLocationpin;
                locationPin.setAddress(address + " " + pincode);
                locationPin.setArea(area);
                locationPin.setMobile(mobileno);
                startActivity(new Intent(AreaAndAddressActivity.this, ParkingDetailsActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }
}
