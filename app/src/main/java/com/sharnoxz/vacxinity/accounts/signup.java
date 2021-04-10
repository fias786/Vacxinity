package com.sharnoxz.vacxinity.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sharnoxz.vacxinity.R;

public class signup extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        SharedPrefs.saveSharedSetting(signup.this,"sharedPrefs","true");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Button button =(Button) findViewById(R.id.Signup_Button1);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        final ProgressBar progressBar = findViewById(R.id.sinUp_progressBar);
        final EditText editText3 = (EditText) findViewById(R.id.Signup_EditText3);
        final EditText editText4 = (EditText) findViewById(R.id.Signup_EditText4);
        final EditText editText5 = (EditText) findViewById(R.id.Signup_EditText5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String email = editText3.getText().toString().trim();
                    String password = editText4.getText().toString().trim();
                    String confirm_password = editText5.getText().toString().trim();

                    if(TextUtils.isEmpty(email)){
                        Toast.makeText(signup.this,"Please enter your email",Toast.LENGTH_SHORT).show();
                        return;
                    }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(signup.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirm_password)){
                    Toast.makeText(signup.this,"Please re-enter your password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length()<6){
                    Toast.makeText(signup.this,"Short Password",Toast.LENGTH_SHORT).show();
                }

                if(password.equals(confirm_password)){
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        String userUid = firebaseAuth.getUid();
                                        Intent intent = new Intent(getApplicationContext(),UserData.class);
                                        intent.putExtra("UserId",userUid);
                                        startActivity(intent);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{

                                        Toast.makeText(signup.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }

            }
        });
    }
}
