package com.service.parking.theparker.Controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.service.parking.theparker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends Activity {

    @BindView(R.id.lb)
    Button mlogin;

    private FirebaseUser firebaseUser;

    private Toolbar mTopBar;

    private CircleImageView mProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTopBar = findViewById(R.id.top_bar);

        setActionBar(mTopBar);
        //getActionBar().setTitle(null);

        mProfileView = findViewById(R.id.custom_bar_image);

        //Check if user is logged in or not
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser == null)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        mlogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        mlogin.setOnLongClickListener(v -> {

            SharedPreferences sh=getSharedPreferences("myinfo",MODE_PRIVATE);
            SharedPreferences.Editor edit = sh.edit();
            edit.clear();

            FirebaseAuth.getInstance().signOut();

            return false;
        });

        mProfileView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

    }
}
