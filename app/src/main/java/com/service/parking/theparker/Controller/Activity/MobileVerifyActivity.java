package com.service.parking.theparker.Controller.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.service.parking.theparker.ActivityAnimator;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MobileVerifyActivity extends Activity {

    @BindView(R.id.btn_phone_verify_next)
    CircleButton mPhoneVerify_btn;

    @BindView(R.id.et_phone_number)
    EditText mPhoneNumber;

    @BindView(R.id.mobile_verify_back_btn)
    ImageView mMobile_verify_back_btn;

    @BindView(R.id.et_name)
    EditText mPersonName;

    private SharedPreferences sh;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verify);
        ButterKnife.bind(this);

        try {
            ActivityAnimator.fadeAnimation(this);
        } catch (Exception ignore) {}

        sh = getSharedPreferences("myinfo",MODE_PRIVATE);
        edit = sh.edit();

        mMobile_verify_back_btn.setOnClickListener(v -> startActivity(new Intent(MobileVerifyActivity.this, LoginActivity.class)));

        mPhoneVerify_btn.setOnClickListener(v -> {
            String Mobile_no= mPhoneNumber.getText().toString().trim();
            String PersonName = mPersonName.getText().toString().trim();

            if(!TextUtils.isEmpty(Mobile_no)) {

                if(Mobile_no.length()==10 || !PersonName.isEmpty())
                {
                    edit.putString(Theparker.SP_Mobileno,Mobile_no);
                    edit.putString(Theparker.SP_Name,PersonName);
                    edit.apply();

                    Theparker.Mobile_no = Mobile_no;
                    Theparker.Person_name = PersonName;

                    Intent otpActivity = new Intent(MobileVerifyActivity.this,OtpVerifyActivity.class);
                    startActivity(otpActivity);
                    finish();
                }

            }
        });

    }
}
