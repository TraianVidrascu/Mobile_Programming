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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i = getIntent().getIntExtra("index",0);
        hardCoded = DummyList.hardCoded.get(i);
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
               hardCoded.setName(name.getText().toString());
               hardCoded.setDescription(description.getText().toString());
               hardCoded.setLocation(location.getText().toString());
               String stringDate = date.getText().toString();
               hardCoded.setDeadline(stringDate);

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

    }

    public void MessageBox(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
