package com.example.ntvid.timemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ntvid.timemanager.dummy.DummyList;
import com.example.ntvid.timemanager.model.Task;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ntvid on 07/11/2017.
 */

public class TaskList extends AppCompatActivity {


    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        listView = (ListView)findViewById(R.id.listView);

        ListAdapter adapter = new ArrayAdapter<Task>(this,
                R.layout.activity_task_list,R.id.textView, DummyList.hardCoded);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent k = new Intent(TaskList.this,TaskDetail.class);
                k.putExtra("index",i);
                startActivity(k);
            }
        });
    }

}
