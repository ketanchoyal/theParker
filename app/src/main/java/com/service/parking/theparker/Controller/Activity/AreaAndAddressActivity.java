package com.service.parking.theparker.Controller.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AreaAndAddressActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_name)
    TextView mActionBarName;

    @BindView(R.id.back_btn)
    CircleImageView mBackBtn;

    @BindView(R.id.next_btn)
    CircleButton mNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_and_address);
        ButterKnife.bind(this);
        Theparker.animate(this);

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
