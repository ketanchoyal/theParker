package com.service.parking.theparker.Controller.Activity.OfferLocation;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ParkingDetailsActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_name)
    TextView mActionBarName;

    @BindView(R.id.back_btn)
    CircleImageView mBackBtn;

    @BindView(R.id.next_btn)
    CircleButton mNextBtn;

    @BindView(R.id.segmentedButtonGroup)
    SegmentedButtonGroup segmentedButtonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);
        ButterKnife.bind(this);

        Theparker.animate(this);

        mActionBarName.setText("Some Details");

        mBackBtn.setOnClickListener(v -> {
            finish();
            Theparker.animate(this);
        });

        mNextBtn.setOnClickListener(v -> {
            startActivity(new Intent(ParkingDetailsActivity.this, PriceAndAdditionalDetailsActivity.class));
        });

        segmentedButtonGroup.setOnClickedButtonListener(position -> {
            if(position == 0){
                Toasty.success(getApplicationContext(),"You selected carpool",Toast.LENGTH_LONG).show();
            }
            else if(position == 1){
                Toasty.success(getApplicationContext(),"You selected ground",Toast.LENGTH_LONG).show();
            }
            else if(position == 2){
                Toasty.success(getApplicationContext(),"You selected garage",Toast.LENGTH_LONG).show();
            }
            else if(position == 3){
                final AlertDialog.Builder builder = new AlertDialog.Builder(ParkingDetailsActivity.this);
                View view = LayoutInflater.from(ParkingDetailsActivity.this).inflate(R.layout.custom_other_dialog,null);
                final EditText fl_other_edit = view.findViewById(R.id.custom_type);
                builder.setView(view);
                final AlertDialog dialog = builder.show();
                Button fl_other_btn = view.findViewById(R.id.custom_button);

                fl_other_btn.setOnClickListener(v -> {
                    if (fl_other_edit.getText().toString().isEmpty()){
                        Toasty.error(getApplicationContext(),"Field cant be empty",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    else{
                        Toasty.info(getApplicationContext(),"Your parking type is "+fl_other_edit.getText().toString(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
             }


        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }


}
