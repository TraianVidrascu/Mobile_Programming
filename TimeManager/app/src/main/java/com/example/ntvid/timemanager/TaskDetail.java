package com.example.ntvid.timemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ntvid.timemanager.dummy.DummyList;
import com.example.ntvid.timemanager.model.Task;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetail extends AppCompatActivity {
    private Task hardCoded;
    private EditText name;
    private EditText description;
    private EditText location;
    private EditText date;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private AppDatabase database;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("index");
        database = AppDatabase.getDatabase(getApplicationContext());
        hardCoded = database.taskDao().findById(id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        name = (EditText)findViewById(R.id.nameText);
        name.setText(hardCoded.getName());
        description = (EditText)findViewById(R.id.description);
        description.setText(hardCoded.getDescription());
        location = (EditText)findViewById(R.id.location);
        location.setText(hardCoded.getLocation());
        date = (EditText)findViewById(R.id.date);
        String formatedDate = hardCoded.getDeadline();
        date.setText(formatedDate);

        Button button = (Button)findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();
                task.setId(id);
                task.setName(name.getText().toString());
                task.setDescription(description.getText().toString());
                task.setLocation(location.getText().toString());
               String stringDate = date.getText().toString();
                task.setDeadline(stringDate);
                database.taskDao().updateTask(task);
               MessageBox("Saved changes!");
            }
        });

        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(TaskDetail.this,TaskList.class);
                startActivity(k);
            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.taskDao().delete(database.taskDao().findById(id));
                Intent k = new Intent(TaskDetail.this,TaskList.class);
                startActivity(k);

            }
        });

    }

    public void MessageBox(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
