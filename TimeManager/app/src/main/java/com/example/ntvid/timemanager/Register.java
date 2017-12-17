package com.example.ntvid.timemanager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ntvid on 08/11/2017.
 */

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullName = (EditText)findViewById(R.id.fullName);
                EditText userName = (EditText)findViewById(R.id.username);
                EditText pass = (EditText)findViewById(R.id.password);

                Intent mail = new Intent(Intent.ACTION_SENDTO);

                String uriText = "mailto:"+Uri.encode("ntvidrascu@gmail.com")+
                        "?subject="+Uri.encode("TimeManager register")+
                        "&body="+Uri.encode("Hi "+ fullName.getText().toString()
                        +",\nusername: "+userName.getText().toString()+"\npassword: "+pass.getText().toString());
                mail.setData(Uri.parse(uriText));

                startActivity(mail);
                finish();
                Log.i("finished sending","");




            }
        });
        Button goToAdd = (Button)findViewById(R.id.goToAdd);
        goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mail = new Intent(Intent.ACTION_SENDTO);

                Intent k = new Intent(Register.this,add_task.class);
                startActivity(k);
            }
        });


        Button list = (Button)findViewById(R.id.seeList);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(Register.this,TaskList.class);
                startActivity(k);
            }
        });
    }

    public void MessageBox(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
