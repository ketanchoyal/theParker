package com.service.parking.theparker.Controller.Activity.OfferLocation;

import android.app.ActivityOptions;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;
import com.suke.widget.SwitchButton;

import java.util.HashMap;
import java.util.Map;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

import static com.service.parking.theparker.Services.NetworkServices.userProfile;

public class ParkingDetailsActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_name)
    TextView mActionBarName;

    @BindView(R.id.back_btn)
    CircleImageView mBackBtn;

    @BindView(R.id.next_btn)
    CircleButton mNextBtn;

    @BindView(R.id.segmentedButtonGroup)
    SegmentedButtonGroup segmentedButtonGroup;

    @BindView(R.id.noOfSpot_edit_text)
    ExtendedEditText mNoOfSpots;

    @BindView(R.id.coveredSwitchBtn)
    SwitchButton mCoveredSwitchBtn;

    @BindView(R.id.staffSwitchBtn)
    SwitchButton mStaffSwitchBtn;

    @BindView(R.id.cameraSwitchBtn)
    SwitchButton mCameraSwitchBtn;

    @BindView(R.id.disabledAccessSwitchBtn)
    SwitchButton mDisabledAcessSwitchBtn;

    String parkingType = "carpool";
    String noOFSpots = "";

    Boolean coveredFeature = false;
    Boolean staffFeature = false;
    Boolean cameraFeature = false;
    Boolean disabledAccessFeature = false;

    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);
        ButterKnife.bind(this);

        Theparker.animate(this);

        mActionBarName.setText("Some Details");

        mBackBtn.setOnClickListener(v -> {
            onBackPressed();
            Theparker.animate(this);
        });

        mNextBtn.setOnClickListener(v -> {
            if (checkData()) {
                Intent toAdditionalDetailPage = new Intent(ParkingDetailsActivity.this, PriceAndAdditionalDetailsActivity.class);
                Pair pair = new Pair<View, String>(mNextBtn,"circleBtn");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
                startActivity(toAdditionalDetailPage,options.toBundle());
            }
        });

        segmentedButtonGroup.setOnClickedButtonListener(position -> {
            if(position == 0){
                pos = position;
                parkingType = "carpool";
                Toasty.info(getApplicationContext(),"Your parking type is Carpool",Toast.LENGTH_LONG).show();
            }
            else if(position == 1){
                pos = position;
                parkingType = "Empty Ground";
                Toasty.info(getApplicationContext(),"Your parking type is ground",Toast.LENGTH_LONG).show();
            }
            else if(position == 2){
                pos = position;
                parkingType = "Garage";
                Toasty.info(getApplicationContext(),"Your parking type is garage",Toast.LENGTH_LONG).show();
            }
            else if(position == 3){
                final AlertDialog.Builder builder = new AlertDialog.Builder(ParkingDetailsActivity.this);
                View view = LayoutInflater.from(ParkingDetailsActivity.this).inflate(R.layout.custom_other_dialog,null);
                final EditText fl_other_edit = view.findViewById(R.id.custom_type);
                builder.setView(view);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.show();
                Button fl_other_btn = view.findViewById(R.id.custom_button);

                fl_other_btn.setOnClickListener(v -> {
                    if (fl_other_edit.getText().toString().isEmpty()){
                        segmentedButtonGroup.setPosition(pos);
                        Toasty.info(getApplicationContext(),"Your parking type is "+parkingType,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    else{
                        pos = position;
                        parkingType = fl_other_edit.getText().toString();
                        Toasty.info(getApplicationContext(),"Your parking type is "+fl_other_edit.getText().toString(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
             }
        });

        mCameraSwitchBtn.setOnCheckedChangeListener((view, isChecked) -> cameraFeature = isChecked);

        mCoveredSwitchBtn.setOnCheckedChangeListener((view, isChecked) -> coveredFeature = isChecked);

        mDisabledAcessSwitchBtn.setOnCheckedChangeListener(((view, isChecked) -> disabledAccessFeature = isChecked));

        mStaffSwitchBtn.setOnCheckedChangeListener(((view, isChecked) -> staffFeature = isChecked));

    }

    boolean checkData() {
        noOFSpots = mNoOfSpots.getEditableText().toString();

        int totalSpots = Integer.parseInt(userProfile.Total_spots);
        int spotsUsed = Integer.parseInt(userProfile.Spots_used);

        if (Integer.parseInt(noOFSpots) > (totalSpots-spotsUsed)) {
            Toasty.error(this,"You have " +(totalSpots-spotsUsed) + "available").show();
            return false;
        }

        if(parkingType.isEmpty() || noOFSpots.isEmpty()) {
            Toasty.error(this,"Please fill the information correctly").show();
            return false;
        } else {
            Map<String,Boolean> features = new HashMap<>();
            features.put("Covered",coveredFeature);
            features.put("Security Camera",cameraFeature);
            features.put("Onsite Staff",staffFeature);
            features.put("Disabled Access",disabledAccessFeature);

            LocationPin locationPin = Theparker.currentLocationpin;

            locationPin.setFeatures(features);
            locationPin.setNumberofspot(noOFSpots);
            locationPin.setType(parkingType);

            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }


}
