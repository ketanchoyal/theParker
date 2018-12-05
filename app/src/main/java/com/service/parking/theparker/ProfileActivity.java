package com.service.parking.theparker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends Activity {

    @BindView(R.id.myProfile_name_et)
    EditText mProfileName;

    @BindView(R.id.myProfile_phone_et)
    EditText mProfileMobileNo;

    @BindView(R.id.myProfile_edit_btn)
    ImageButton mProfileEditbtn;

    @BindView(R.id.myProfile_email_et)
    EditText mProfileEmail;

    @BindView(R.id.myProfile_back_btn)
    ImageButton mProfileBackbtn;

    //Constants
    private Boolean isEditEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        UI_Update();

        mProfileName.setText(Theparker.Person_name);
        mProfileMobileNo.setText(Theparker.Mobile_no);

        Log.d("Name Mobile_No : ", Theparker.Person_name + Theparker.Mobile_no);

        mProfileEditbtn.setOnClickListener(v -> {

            UI_Update();

        });

        mProfileBackbtn.setOnClickListener(v ->{
            finish();
        });

    }

    void UI_Update() {

        if (isEditEnable) {

            isEditEnable = false;
            mProfileEmail.setEnabled(true);
            mProfileEmail.setFocusable(true);
            mProfileName.setEnabled(true);
            mProfileName.setFocusable(true);
            mProfileMobileNo.setEnabled(true);
            mProfileMobileNo.setFocusable(true);

        } else {

            isEditEnable = true;
            mProfileEmail.setEnabled(false);
            mProfileEmail.setFocusable(false);
            mProfileName.setEnabled(false);
            mProfileName.setFocusable(false);
            mProfileMobileNo.setEnabled(false);
            mProfileMobileNo.setFocusable(false);

        }

    }
}