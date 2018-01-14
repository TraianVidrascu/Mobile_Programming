package com.example.ntvid.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ntvid.taskmanager.api.FirebaseService;
import com.example.ntvid.taskmanager.database.AppDatabase;
import com.example.ntvid.taskmanager.model.Task;

public class AddActivity extends AppCompatActivity {
    //private AppDatabase database;
    private FirebaseService firebaseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //database = AppDatabase.getDatabase(getApplicationContext());
        firebaseService = FirebaseService.getInstance();

        Button register = (Button)findViewById(R.id.add_submit);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText)findViewById(R.id.add_name)).getText().toString();
                String desc = ((EditText)findViewById(R.id.add_desc)).getText().toString();
                String location = ((EditText)findViewById(R.id.add_location)).getText().toString();
                String deadline = ((EditText)findViewById(R.id.add_date)).getText().toString();


                Task task = new Task(name,name,desc,location,deadline);
                //database.taskDao().addTask(task);
                firebaseService.addTask(task);
                Intent k = new Intent(AddActivity.this,ListActivity.class);
                startActivity(k);




            }
        });
    }
}
