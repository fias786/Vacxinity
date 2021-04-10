package com.sharnoxz.vacxinity.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.sharnoxz.vacxinity.R;

public class forgetpassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        SharedPrefs.saveSharedSetting(forgetpassword.this,"sharedPrefs","true");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Button button =(Button) findViewById(R.id.FP_Button1);
        final EditText editText1 = findViewById(R.id.FP_EditText1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText1.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(forgetpassword.this,"Enter email address",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                            firebaseAuth.sendPasswordResetEmail(email)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(forgetpassword.this, login.class);
                                            forgetpassword.this.startActivity(intent);
                                            Toast.makeText(forgetpassword.this,"Check mail",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(forgetpassword.this,"Enter registered mail",Toast.LENGTH_SHORT).show();
                                        }
                                    });

                }
            }
        });
    }
}
