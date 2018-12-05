package com.service.parking.theparker.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.service.parking.theparker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlacesActivity extends MainActivity {

    @BindView(R.id.lb)
    Button mlogin;

    private Toolbar mTopBar;

    private CircleImageView mProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        ButterKnife.bind(this);

        mProfileView = findViewById(R.id.custom_bar_image);


        mlogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        mlogin.setOnLongClickListener(v -> {

            SharedPreferences sh=getSharedPreferences("myinfo",MODE_PRIVATE);
            SharedPreferences.Editor edit = sh.edit();
            edit.clear();

            FirebaseAuth.getInstance().signOut();

            return false;
        });

        mProfileView.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_places;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.places_activity;
    }

}
