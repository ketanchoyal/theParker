package com.service.parking.theparker.Controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.service.parking.theparker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public abstract class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{



    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();

    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        //ButterKnife.bind(this);

        //mTopBar = findViewById(R.id.top_bar);

        //setActionBar(mTopBar);
        //getActionBar().setTitle(null);

        navigationView = findViewById(R.id.bottomNavView_Bar);
        navigationView.setOnNavigationItemSelectedListener(this);

        //mProfileView = findViewById(R.id.custom_bar_image);

        //Check if user is logged in or not
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(firebaseUser == null)
//        {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish();
//        }

//        mlogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
//
//        mlogin.setOnLongClickListener(v -> {
//
//            SharedPreferences sh=getSharedPreferences("myinfo",MODE_PRIVATE);
//            SharedPreferences.Editor edit = sh.edit();
//            edit.clear();
//
//            FirebaseAuth.getInstance().signOut();
//
//            return false;
//        });

        //mProfileView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

//    void selectBottomNavigationBarItem(int itemId) {
//        Menu menu = navigationView.getMenu();
//        for (int i = 0, size = menu.size(); i < size; i++) {
//            MenuItem item = menu.getItem(i);
//            boolean shouldBeChecked = item.getItemId() == itemId;
//            if (shouldBeChecked) {
//                item.setChecked(true);
//                break;
//            }
//        }
//    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.places_activity:
                startActivity(new Intent(this, PlacesActivity.class));
                break;
            case R.id.map_activity:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.bookings_activity:
                //startActivity(new Intent(this, AccountsActivity.class));
                //break;
            case R.id.wallet_activity:
                //startActivity(new Intent(this, AccountsActivity.class));
                //break;
            case R.id.setting_activity:
                //startActivity(new Intent(this, AccountsActivity.class));
                //break;
        }
        finish();
        return true;
    }

}
