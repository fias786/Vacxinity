package com.sharnoxz.vacxinity.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sharnoxz.vacxinity.MainActivity;
import com.sharnoxz.vacxinity.R;

public class login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        TextView textview3 =(TextView) findViewById(R.id.Login_Textview3);
        final EditText editText1 = (EditText) findViewById(R.id.Login_EditText1);
        final EditText editText2 = (EditText) findViewById(R.id.Login_EditText2);
        final ProgressBar progressBar = findViewById(R.id.Login_progressBar);
        editText1.setText("");
        editText2.setText("");
        textview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(login.this,signup.class);
                login.this.startActivity(intent);
                finish();
            }
        });

        TextView textview2 =(TextView) findViewById(R.id.Login_Textview2);
        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, forgetpassword.class);
                login.this.startActivity(intent);
                finish();
            }
        });

        Button button = (Button) findViewById(R.id.Login_Button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editText1.getText().toString().trim();
                String password = editText2.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(login.this,"Please enter your Email",Toast.LENGTH_SHORT).show();
                    return;
                }if(TextUtils.isEmpty(password)){
                    Toast.makeText(login.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(login.this,"Short Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            FirebaseUser user;
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    user = firebaseAuth.getCurrentUser();
                                    String email = user.getEmail();
                                    SharedPrefs.saveSharedSetting(login.this,"sharedPrefs","false");
                                    startActivity(new Intent(login.this, MainActivity.class));
                                    finish();

                                }
                                else{
                                    Toast.makeText(login.this,"Wrong Email or Password",Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

            }
        });
    }

}

