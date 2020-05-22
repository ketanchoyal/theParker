package com.service.parking.theparker.Controller.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.service.parking.theparker.Controller.Activity.Register.LoginActivity;
import com.service.parking.theparker.Controller.Fragment.BookingFragment;
import com.service.parking.theparker.Controller.Fragment.OfferPlaceFragment;
import com.service.parking.theparker.Controller.Fragment.PackagesFragment;
import com.service.parking.theparker.Controller.Fragment.PlacesFragment;
import com.service.parking.theparker.Controller.Fragment.WalletFragment;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.View.ActivityAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.framelayout)
    FrameLayout fragmentFrame;

    @BindView(R.id.bottomNavView_Bar)
    BottomNavigationViewEx navigation;

    TextView mEmail;
    TextView mName;
    private String fragmentName;
    CircleImageView mProfileImageView;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.custom_bar_image)
    CircleImageView mProfileView;

    @BindView(R.id.fragment_name)
    TextView mFragmentName;

    int position = 0;

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationDrawerItemSelectedListener
            = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.profile_activity:
                return profileActivity();

            case R.id.setting_activity:
                return false;

            case R.id.transaction_activity:
                return false;

            case R.id.logout_btn:
                return logout();

            default:
                return false;
        }
    };

    private boolean profileActivity() {
        Intent toProfile = new Intent(this, ProfileActivity.class);
        Pair pair = new Pair<View, String>(mProfileImageView,"circleImage");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
        startActivity(toProfile,options.toBundle());
        return false;
    }

    private boolean logout() {
//        SharedPreferences sh = getSharedPreferences("myinfo",MODE_PRIVATE);
//        SharedPreferences.Editor edit = sh.edit();
//        edit.clear();

        FirebaseAuth.getInstance().signOut();
        gotToLogin();
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.places_fragment:
                        fragmentName = "Places";
                        fragment = new PlacesFragment();
                        position = 0;
                        break;

                    case R.id.packages_fragment:
                        fragmentName = "Packages";
                        fragment = new PackagesFragment();
                        position = 4;
                        break;

                    case R.id.bookings_fragment:
                        fragmentName = "Bookings";
                        fragment = new BookingFragment();
                        position = 1;
                        break;

                    case R.id.wallet_fragment:
                        fragmentName = "Wallet";
                        fragment = new WalletFragment();
                        position = 2;
                        break;

                    case R.id.offer_place_fragment:
                        fragmentName = "Offer Place";
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
        ButterKnife.bind(this);

        View headerView = navigationView.getHeaderView(0);

        mEmail = headerView.findViewById(R.id.email_view);
        mName = headerView.findViewById(R.id.name_view);
        mProfileImageView = headerView.findViewById(R.id.profileimageView);

        mProfileImageView.setOnClickListener(v -> profileActivity());

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            NetworkServices.ProfileData.setData(mName,mEmail,null);
        }

        try {
            ActivityAnimator.fadeAnimation(this);
        } catch (Exception ignore) {}

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.enableAnimation(true);
        navigation.setTextVisibility(true);

        int position = getIntent().getIntExtra("position",0);

        navigation.setCurrentItem(position);

        navigationView.setNavigationItemSelectedListener(mOnNavigationDrawerItemSelectedListener);

        mProfileView.setOnClickListener(v1 -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

    }

    public boolean loadFragment(Fragment fragment,int position) {
        //switching fragment
        if (fragment != null) {
            mFragmentName.setText(fragmentName);
            navigation.enableShiftingMode(position,true);
            navigation.setItemBackgroundResource(R.color.colorPrimaryDark);
            navigation.setItemBackground(position,R.color.colorPrimary);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
           gotToLogin();
        }
    }

    void gotToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Theparker.animate(this);
        }
    }
}
