package com.example.ntvid.taskmanager;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ntvid.taskmanager.api.FirebaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseService firebaseService;
    private FirebaseAuth auth;
    private EditText email;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        firebaseService = FirebaseService.getInstance();
        email = findViewById(R.id.email_input_text);
        pass = findViewById(R.id.password_input_text);

        Button login = (Button) findViewById(R.id.go_to_login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        Button register = (Button) findViewById(R.id.do_register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailText = email.getText().toString();
                String passText = pass.getText().toString();
                if(!mailText.equals("") && !passText.equals("")){
                    auth.createUserWithEmailAndPassword(mailText,passText).addOnCompleteListener(
                            RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        task.getResult().getUser()
                                        FirebaseUser user =
                                        user.
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Log.w("tag", "registerInWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Register failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }



            }
        });

        Button mail = (Button) findViewById(R.id.mail_button);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = (EditText)findViewById(R.id.email_input_text);
                EditText pass = (EditText)findViewById(R.id.password_input_text);

                Intent mail = new Intent(Intent.ACTION_SENDTO);

                String uriText = "mailto:"+ Uri.encode(email.getText().toString())+
                        "?subject="+Uri.encode("TimeManager register")+
                        "&body="+Uri.encode("Hi "+"\npassword: "+pass.getText().toString());
                mail.setData(Uri.parse(uriText));

                startActivity(mail);
                finish();
            }
        });
    }
}
