package com.example.ntvid.timemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /*  Button goToAdd = (Button)findViewById(R.id.goToAdd);
        goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent k = new Intent(MainActivity.this,add_task.class);
                startActivity(k);
            }
        });

        Button list = (Button)findViewById(R.id.seeList);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(MainActivity.this,TaskList.class);
                startActivity(k);
            }
        });

        Button toChart = (Button)findViewById(R.id.toChart);
        toChart.setOnClickListener((view)->{
            Intent k = new Intent(MainActivity.this,Chart.class);
            startActivity(k);
        });*/
    }
}
