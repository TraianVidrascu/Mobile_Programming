package com.example.ntvid.timemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ntvid.timemanager.dao.TaskDao;
import com.example.ntvid.timemanager.dummy.DummyList;
import com.example.ntvid.timemanager.model.Task;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ntvid on 07/11/2017.
 */

public class TaskList extends AppCompatActivity {

    private AppDatabase database;

    private ListView listView;

    @Override
    protected void onResume() {
        List<Task> list = new ArrayList<>();
        list = database.taskDao().getAll();
        listView = (ListView)findViewById(R.id.listView);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        database = AppDatabase.getDatabase(getApplicationContext());

        List<Task> list = new ArrayList<>();
        list = database.taskDao().getAll();
        if(list.size()==0){
            Intent k = new Intent(TaskList.this,add_task.class);
            startActivity(k);
            this.finish();
        }
        listView = (ListView)findViewById(R.id.listView);

        ListAdapter adapter = new ArrayAdapter<Task>(this,
                R.layout.activity_task_list,R.id.textView, list);
        listView.setAdapter(adapter);



        final List<Task> finalList = list;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent k = new Intent(TaskList.this,TaskDetail.class);
                k.putExtra("index", finalList.get(i).getId());
                startActivity(k);
            }
        });







    }

}
