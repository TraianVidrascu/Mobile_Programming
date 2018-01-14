package com.example.ntvid.timemanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ntvid.timemanager.dummy.DummyList;
import com.example.ntvid.timemanager.model.Task;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskDetail extends AppCompatActivity implements View.OnClickListener {
    private Task hardCoded;
    private EditText name;
    private EditText description;
    private EditText location;
    private TextView date;
    private Button changeDate;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private AppDatabase database;
    private String id;
    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("index");
        database = AppDatabase.getDatabase(getApplicationContext());
        hardCoded = database.taskDao().findById(id);
        setContentView(R.layout.activity_task_detail);
        name = (EditText) findViewById(R.id.nameText);
        name.setText(hardCoded.getName());
        description = (EditText) findViewById(R.id.description);
        description.setText(hardCoded.getDescription());
        location = (EditText) findViewById(R.id.location);
        location.setText(hardCoded.getLocation());
        date = (TextView) findViewById(R.id.date);
        changeDate = (Button) findViewById(R.id.changeDate);
        String formatedDate = hardCoded.getDeadline();
        if (!formatedDate.matches("[a-zA-z]")) ;
        date.setText(formatedDate);
        changeDate.setOnClickListener(this);

        Button button = (Button) findViewById(R.id.save);
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

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",database.taskDao().findById(id));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.taskDao().delete(database.taskDao().findById(id));
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",new Task());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

    public void MessageBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year));
            }
        },year,month,day);
        dialog.show();
    }
}
