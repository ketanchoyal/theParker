package com.service.parking.theparker.Controller.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.service.parking.theparker.Controller.Activity.Register.LoginActivity;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.the_parker_icon)
    ImageView mParkerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        Theparker.animate(this);

        new Handler().postDelayed(() -> {
            if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                gotToLogin();
            } else {
                gotToStart();
            }
        },3000);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    void gotToLogin() {
        Intent toLogin = new Intent(this, LoginActivity.class);
        Pair pair = new Pair<View, String>(mParkerIcon,"parkerImage");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
        startActivity(toLogin,options.toBundle());
        finish();
    }

    void gotToStart() {
        Intent toStart = new Intent(this, StartActivity.class);
        Pair pair = new Pair<View, String>(mParkerIcon,"parkerImage");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
        startActivity(toStart,options.toBundle());
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }
}
