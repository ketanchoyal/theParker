package com.service.parking.theparker_adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

import at.markushi.ui.CircleButton;

public class Main2Activity extends AppCompatActivity {

    EditText admin_id,admin_password;
    CircleButton btn_next;
    FirebaseAuth firebaseAuth;
    AVLoadingIndicatorView avi;
    Button e_wallet;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_next = findViewById(R.id.btn_phone_verify_next);
        admin_id=findViewById(R.id.admin_id);
        admin_password=findViewById(R.id.admin_password);
        btn_next = findViewById(R.id.btn_phone_verify_next);
        e_wallet = findViewById(R.id.E_wallet);
        firebaseAuth = FirebaseAuth.getInstance();
        avi=findViewById(R.id.avi);

        e_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,Form_layoutActivity.class);
                startActivity(i);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avi.show();

                String id = admin_id.getText().toString().trim();
                String pass = admin_password.getText().toString().trim();

                if(id.isEmpty() && pass.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fields Can't be Empty",Toast.LENGTH_LONG).show();
                } else {
                    signinWithEmail(id,pass);
                }
            }
        });
    }
    void sendToStart() {
        avi.hide();
        Intent i = new Intent(Main2Activity.this,Main3Activity.class);
        startActivity(i);
        finish();
    }

    void signinWithEmail(String email, String pass) {
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                sendToStart();
            }
        });
    }

}
