package com.example.ntvid.timemanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ntvid.timemanager.model.Task;

public class add_task extends AppCompatActivity {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        database = AppDatabase.getDatabase(getApplicationContext());

        Button register = (Button)findViewById(R.id.taskSubmit);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText)findViewById(R.id.taskName)).getText().toString();
                String desc = ((EditText)findViewById(R.id.taskDesc)).getText().toString();
                String location = ((EditText)findViewById(R.id.taskLocation)).getText().toString();
                String deadline = ((EditText)findViewById(R.id.taskDeadline)).getText().toString();


                Task task = new Task(name,name,desc,location,deadline);
                database.taskDao().addTask(task);
                Intent k = new Intent(add_task.this,TaskList.class);
                startActivity(k);




            }
        });
    }
}
