package com.example.ntvid.taskmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import com.example.ntvid.taskmanager.api.FirebaseService;
import com.example.ntvid.taskmanager.database.AppDatabase;
import com.example.ntvid.taskmanager.model.Task;
import com.example.ntvid.taskmanager.model.Watch;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Task hardCoded;
    private EditText name;
    private EditText description;
    private EditText location;
    private TextView date;
    private Button changeDate;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    //private AppDatabase database;
    private FirebaseService firebaseService;
    private FirebaseAuth auth;
    private String id;
    private int year;
    private int month;
    private int day;
    private Watch watcher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("index");
        auth = FirebaseAuth.getInstance();
        firebaseService = FirebaseService.getInstance();
        hardCoded = firebaseService.findById(id);
        setContentView(R.layout.activity_detail);
        name = (EditText) findViewById(R.id.detail_name);
        name.setText(hardCoded.getName());
        description = (EditText) findViewById(R.id.detail_desc);
        description.setText(hardCoded.getDescription());
        location = (EditText) findViewById(R.id.detail_location);
        location.setText(hardCoded.getLocation());
        date = (TextView) findViewById(R.id.detail_date);
        changeDate = (Button) findViewById(R.id.detail_date_change);
        String formatedDate = hardCoded.getDeadline();
        if (!formatedDate.matches("[a-zA-z]")) ;
        date.setText(formatedDate);
        changeDate.setOnClickListener(this);
        watcher = firebaseService.findByTaskAndUSer(auth.getCurrentUser().getUid(),id);

        Button button = (Button) findViewById(R.id.detail_save);
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
                //database.taskDao().updateTask(task);
                firebaseService.updateTask(task);
                MessageBox("Saved changes!");
            }
        });

        Button back = (Button) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",/*database.taskDao().findById(id)*/firebaseService.findById(id));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        Button delete = (Button) findViewById(R.id.detail_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //database.taskDao().delete(database.taskDao().findById(id));
                firebaseService.deleteTask(id);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",new Task());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        Button watch = (Button) findViewById(R.id.watch_task_detail);
        Button unfollow = (Button) findViewById(R.id.unfollow_task_detail);

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watcher = firebaseService.addWatch(new Watch(auth.getCurrentUser().getUid(),id));
                unfollow.setVisibility(View.VISIBLE);
                watch.setVisibility(View.GONE);

            }
        });

        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseService.deleteWatch(watcher.getId());
                unfollow.setVisibility(View.GONE);
                watch.setVisibility(View.VISIBLE);
                watcher = null;

            }
        });

        if(watcher!=null){
            watch.setVisibility(View.GONE);
        }else {
            unfollow.setVisibility(View.GONE);
        }

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

