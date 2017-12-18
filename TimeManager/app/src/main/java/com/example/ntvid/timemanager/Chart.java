package com.example.ntvid.timemanager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chart extends AppCompatActivity {
    private AppDatabase appDatabase;
    private BarChart barChart;
    private ArrayList<String> labels = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLabels();
        setContentView(R.layout.activity_chart);
        appDatabase = AppDatabase.getDatabase(getApplicationContext());
        barChart = findViewById(R.id.chart);



        //set x axis
        XAxis xAxis = barChart.getXAxis();
        /*xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);;*/
        xAxis.setGranularity(1f);
        /*xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12f);*/
// set a custom value formatter
        xAxis.setValueFormatter((value, axis) -> labels.get((int) value));

        //entries
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f,appDatabase.taskDao().getAll().size()));
        BarDataSet barDataSet = new BarDataSet(entries,"tasks");

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1f); // set custom bar width
        barChart.setData(barData);
        barChart.setFitBars(true);

        barChart.invalidate(); // refresh
    }


    private void initLabels(){
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
    }
}
