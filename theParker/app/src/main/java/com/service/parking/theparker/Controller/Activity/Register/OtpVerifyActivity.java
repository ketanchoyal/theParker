package com.service.parking.theparker.Controller.Activity.Register;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.google.android.material.snackbar.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.service.parking.theparker.Controller.Activity.ProfileActivity;
import com.service.parking.theparker.Controller.Activity.StartActivity;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpVerifyActivity extends Activity {

    @BindView(R.id.et_otp_num1)
    EditText et_no1;

    @BindView(R.id.et_otp_num2)
    EditText et_no2;

    @BindView(R.id.et_otp_num3)
    EditText et_no3;

    @BindView(R.id.et_otp_num4)
    EditText et_no4;

    @BindView(R.id.et_otp_num5)
    EditText et_no5;

    @BindView(R.id.et_otp_num6)
    EditText et_no6;

    @BindView(R.id.btn_otp_verify_next)
    CircleButton mOtp_verify_btn;

    @BindView(R.id.btn_otp_resend)
    Button mResend_btn;

    @BindView(R.id.btn_otp_change_number)
    Button mChange_no_btn;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mUserDatabase;

    private String OTP = null;
    private String mVerificationId = null;

//    private SharedPreferences sh;
//    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        ButterKnife.bind(this);

        Theparker.animate(this);

        firebaseAuth=FirebaseAuth.getInstance();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

//        sh = getSharedPreferences("myinfo",MODE_PRIVATE);
//        edit = sh.edit();
//        edit.apply();

        final String Mobile_no = Theparker.Mobile_no;

        //Log.d("Mobile no : ",Mobile_no);

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                OTP = phoneAuthCredential.getSmsCode();

                //Log.d("OTP : ",OTP);

                //Autofill OTP in the EditText Fields
                if(OTP!=null)
                {
                    et_no1.setText(String.valueOf(OTP.charAt(0)));
                    et_no2.setText(String.valueOf(OTP.charAt(1)));
                    et_no3.setText(String.valueOf(OTP.charAt(2)));
                    et_no4.setText(String.valueOf(OTP.charAt(3)));
                    et_no5.setText(String.valueOf(OTP.charAt(4)));
                    et_no6.setText(String.valueOf(OTP.charAt(5)));
                }

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Snackbar.make(findViewById(android.R.id.content),e.getMessage(),Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Snackbar.make(findViewById(android.R.id.content),"Code is Sent on "+Mobile_no,Snackbar.LENGTH_LONG).show();

                mVerificationId = s;
                mResendToken = forceResendingToken;


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };

        startPhoneNumberVerification(Mobile_no);

        //Change focus to adjacent field after entering no in the OTP field
        et_no1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(et_no1.getText().toString().length() == 1)
                {
                    et_no2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_no2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(et_no2.getText().toString().length() == 1)
                {
                    et_no3.requestFocus();
                }

                if(et_no2.getText().toString().length() == 0)
                {
                    et_no1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_no3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(et_no3.getText().toString().length() == 1)
                {
                    et_no4.requestFocus();
                }

                if(et_no3.getText().toString().length() == 0)
                {
                    et_no2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_no4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(et_no4.getText().toString().length() == 0)
                {
                    et_no3.requestFocus();
                }
                if(et_no4.getText().toString().length() == 1)
                {
                    et_no5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_no5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(et_no5.getText().toString().length() == 0)
                {
                    et_no4.requestFocus();
                }
                if(et_no5.getText().toString().length() == 1)
                {
                    et_no6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_no6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(et_no6.getText().toString().length() == 0)
                {
                    et_no5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mOtp_verify_btn.setOnClickListener(v -> {

            if(et_no1.getText().toString().equals("") || et_no2.getText().toString().equals("") || et_no3.getText().toString().equals("")
                    || et_no4.getText().toString().equals("") || et_no5.getText().toString().equals("") || et_no6.getText().toString().equals("") )
            {
                Snackbar.make(findViewById(android.R.id.content), "Please Enter OTP", Snackbar.LENGTH_LONG).show();
            }

            else if (mVerificationId == null)
            {
                Snackbar.make(findViewById(android.R.id.content), "Please wait while we send you the correct OTP", Snackbar.LENGTH_LONG).show();
            }

            else
            {
                if(true)
                {
                    OTP = et_no1.getText().toString()+et_no2.getText().toString()+et_no3.getText().toString()+et_no4.getText().toString()+et_no5.getText().toString()+et_no6.getText().toString();
                }

                verifyPhoneNumberWithCode(mVerificationId,OTP);
            }

        });

        mResend_btn.setOnClickListener(v -> resendVerificationCode(Mobile_no,mResendToken));

        mChange_no_btn.setOnClickListener(v -> {

            onBackPressed();

        });

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        reverseTimer(30, mResend_btn);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful())
                    {
                        //task.getResult().getAdditionalUserInfo().isNewUser()
                        final Map<String,String> UserdataMap =new HashMap<>();
                        UserdataMap.put("Name",Theparker.Person_name);
                        UserdataMap.put("Mobile_no",Theparker.Mobile_no);
                        UserdataMap.put("Total_spots","0");
                        UserdataMap.put("Spots_used","0");
                        UserdataMap.put("Balance","500");
                        UserdataMap.put("Earnings","0");

                        String User_id = FirebaseAuth.getInstance().getUid();

                        //Log.d("User id : ",User_id);
//                        edit.putString(Theparker.SP_User_id,User_id);
//                        edit.apply();

                        //final String[] device_token = new String[1];
                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {

                            String Token = instanceIdResult.getToken().trim();
                            UserdataMap.put("device_token", Token);


                        });

                        if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                            mUserDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile").setValue(UserdataMap).addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful())
                                {
                                    Intent profileIntent = new Intent(OtpVerifyActivity.this, ProfileActivity.class);
                                    profileIntent.putExtra("from",true);

                                    Pair pair = new Pair<View, String>(mOtp_verify_btn,"loginTransition");
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);

                                    startActivity(profileIntent,options.toBundle());
                                    finish();
                                    Theparker.animate(this);
                                }
                                else
                                {
                                    Snackbar.make(findViewById(android.R.id.content), Objects.requireNonNull(task1.getException()).getMessage(), Snackbar.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Intent mainIntent = new Intent(OtpVerifyActivity.this, StartActivity.class);

                            Pair pair = new Pair<View, String>(mOtp_verify_btn,"loginTransition");
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);

                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent,options.toBundle());
                            finish();
                            Theparker.animate(this);
                        }

                    }
                    else {
                        // Sign in failed, display a message and update the UI
                        Snackbar.make(findViewById(android.R.id.content), Objects.requireNonNull(task.getException()).getMessage(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        mResend_btn.setEnabled(false);
        reverseTimer(60, mResend_btn);
    }

    public void reverseTimer(int Seconds,final Button btn){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                btn.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                btn.setText("Resend");
                btn.setEnabled(true);
            }
        }.start();
    }
}
