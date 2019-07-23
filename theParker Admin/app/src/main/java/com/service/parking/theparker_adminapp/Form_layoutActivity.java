package com.service.parking.theparker_adminapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import es.dmoral.toasty.Toasty;

public class Form_layoutActivity extends AppCompatActivity   {

    Button form_layout1_btn_next;
    SegmentedButtonGroup segmentedButtonGroup;
    EditText fl_edit_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_layout);
        fl_edit_number = findViewById(R.id.fl_edit_number);

        form_layout1_btn_next =findViewById(R.id.form_layout1_btn_next);
        form_layout1_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Form_layoutActivity.this,Form_LayoutActivity2.class);
                startActivity(i);
            }
        });
        segmentedButtonGroup=  findViewById(R.id.segmentedButtonGroup);
        segmentedButtonGroup.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Form_layoutActivity.this);
                View view = LayoutInflater.from(Form_layoutActivity.this).inflate(R.layout.custom_other,null);

                final EditText fl_other_edit = view.findViewById(R.id.custom_type);
                final Dialog dialog = new Dialog(getApplicationContext());
                Button fl_other_btn = view.findViewById(R.id.custom_button);

                fl_other_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fl_other_edit.getText().toString().isEmpty()){
                            Toasty.error(getApplicationContext(),"Field cant be empty",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toasty.info(getApplicationContext(),"Your land type is "+fl_other_edit.getText().toString(),Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();


                    }
                });
                builder.setView(view);
                builder.show();
            }


            }
        });
    }

}
