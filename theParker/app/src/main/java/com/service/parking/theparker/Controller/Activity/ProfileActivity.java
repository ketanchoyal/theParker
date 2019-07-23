package com.service.parking.theparker.Controller.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;
import com.service.parking.theparker.Theparker;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @BindView(R.id.myProfile_image_iv)
    CircleImageView myProfileImageIv;

    @BindView(R.id.myProfile_save_btn)
    Button myProfileSaveBtn;

    //PackageConstants
    private Boolean isEditEnable = true;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    Boolean fromLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Theparker.animate(this);

        NetworkServices.ProfileData.setData(mProfileName, mProfileEmail, mProfileMobileNo);
        NetworkServices.ProfileData.getProfileData();

        Log.d("Name Mobile_No : ", Theparker.Person_name + Theparker.Mobile_no);

        mProfileEditbtn.setOnClickListener(v -> {

            //checkPermission();

            //NetworkServices.ProfileData.init(mProfileName.getText().toString(), mProfileEmail.getText().toString(), getApplicationContext());

            Log.d("Name : ", mProfileName.getText().toString());
            UI_Update();


        });

        fromLogin = getIntent().getBooleanExtra("from", false);

        if (fromLogin) {
            mProfileBackbtn.setVisibility(View.INVISIBLE);
        } else {
            mProfileBackbtn.setVisibility(View.VISIBLE);
        }

        mProfileBackbtn.setOnClickListener(v -> {
            onBackPressed();
            Theparker.animate(this);
        });

        myProfileSaveBtn.setOnClickListener(v -> {
            isEditEnable = false;
            UI_Update();
        });

    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    void UI_Update() {

        if (isEditEnable) {

            isEditEnable = false;
            mProfileEmail.setEnabled(true);
            mProfileEmail.setFocusable(true);
            mProfileName.setEnabled(true);
            mProfileName.setFocusable(true);

            mProfileEditbtn.setBackgroundResource(R.drawable.icon_save);

            //NetworkServices.ProfileData.init(mProfileName.getText().toString(), mProfileEmail.getText().toString(), this);

        } else {

            isEditEnable = true;
            mProfileEmail.setEnabled(false);
            mProfileEmail.setFocusable(false);
            mProfileName.setEnabled(false);
            mProfileName.setFocusable(false);

            NetworkServices.ProfileData.updateData(mProfileName.getText().toString(), mProfileEmail.getText().toString(), this);

            mProfileEditbtn.setBackgroundResource(R.drawable.icon_edit);

            if (fromLogin) {
                startActivity(new Intent(this, StartActivity.class));
                finish();
                Theparker.animate(this);
            }

        }

    }
}