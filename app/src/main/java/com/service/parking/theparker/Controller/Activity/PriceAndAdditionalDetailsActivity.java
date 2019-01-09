package com.service.parking.theparker.Controller.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.View.ActivityAnimator;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PriceAndAdditionalDetailsActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_name)
    TextView mActionBarName;

    @BindView(R.id.back_btn)
    CircleImageView mBackBtn;

    @BindView(R.id.finish_btn)
    CircleButton mFinishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_and_additional_details);
        ButterKnife.bind(this);

        Theparker.animate(this);

        mActionBarName.setText("Price and Additional Details");

        mBackBtn.setOnClickListener(v -> {
            finish();
            Theparker.animate(this);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }
}
