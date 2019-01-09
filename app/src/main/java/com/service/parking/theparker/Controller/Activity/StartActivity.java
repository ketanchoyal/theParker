package com.service.parking.theparker.Controller.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.service.parking.theparker.View.ActivityAnimator;
import com.service.parking.theparker.Controller.Fragment.BookingFragment;
import com.service.parking.theparker.Controller.Fragment.PackagesFragment;
import com.service.parking.theparker.Controller.Fragment.PlacesFragment;
import com.service.parking.theparker.Controller.Fragment.OfferPlaceFragment;
import com.service.parking.theparker.Controller.Fragment.WalletFragment;
import com.service.parking.theparker.R;

public class StartActivity extends AppCompatActivity {

    FrameLayout fragmentFrame;
    BottomNavigationViewEx navigation;
    int position=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.places_fragment:
                        fragment = new PlacesFragment();
                        position = 0;
                        break;

                    case R.id.packages_fragment:
                        fragment = new PackagesFragment();
                        position = 4;
                        break;

                    case R.id.bookings_fragment:
                        fragment = new BookingFragment();
                        position = 1;
                        break;

                    case R.id.wallet_fragment:
                        fragment = new WalletFragment();
                        position = 2;
                        break;

                    case R.id.offer_place_fragment:
                        fragment = new OfferPlaceFragment();
                        position = 3;
                        break;
                }
                return loadFragment(fragment,position);
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        try {
            ActivityAnimator.fadeAnimation(this);
        } catch (Exception ignore) {}

        fragmentFrame = findViewById(R.id.framelayout);

        navigation = findViewById(R.id.bottomNavView_Bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.enableAnimation(true);
        navigation.setTextVisibility(true);
        navigation.setCurrentItem(0);
//        loadFragment(new PlacesFragment(),position);
    }

    private boolean loadFragment(Fragment fragment,int position) {
        //switching fragment
        if (fragment != null) {

            navigation.enableShiftingMode(position,true);
            navigation.setItemBackgroundResource(R.color.colorPrimaryDark);
            navigation.setItemBackground(position,R.color.colorPrimary);
//            navigation.enableItemShiftingMode(true);
//            navigation.enableShiftingMode(true);
//            navigation.setTextVisibility(false);
//            navigation.setT

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
