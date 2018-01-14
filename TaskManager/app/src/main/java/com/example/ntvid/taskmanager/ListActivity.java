package com.example.ntvid.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.example.ntvid.taskmanager.api.FirebaseService;
import com.example.ntvid.taskmanager.database.AppDatabase;
import com.example.ntvid.taskmanager.model.Task;

public class ListActivity extends AppCompatActivity implements Observer{

    private static int UPDATE = 1;
    //private AppDatabase database;
    private FirebaseService firebaseService;
    private List<Task> list = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onResume() {

        //list = database.taskDao().getAll();
        list = firebaseService.getAll();
        firebaseService.getObservers().add(this);
        listView = (ListView) findViewById(R.id.task_list);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        firebaseService = FirebaseService.getInstance();
        list = firebaseService.getAll();

        /*if (list.size() == 0) {
            Intent k = new Intent(ListActivity.this, AddActivity.class);
            startActivity(k);
            this.finish();
        }*/
        listView = (ListView) findViewById(R.id.task_list);

        adapter = new ArrayAdapter<Task>(this,
                R.layout.activity_list, R.id.textView, list);
        listView.setAdapter(adapter);


        final List<Task> finalList = list;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent k = new Intent(ListActivity.this, DetailActivity.class);
                k.putExtra("index", finalList.get(i).getId());
                startActivityForResult(k, UPDATE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Task result = (Task) data.getParcelableExtra("result");
                list = firebaseService.getAll();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    listView.invalidateViews();
                    listView.invalidate();
                    this.recreate();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        this.recreate();
    }
}
