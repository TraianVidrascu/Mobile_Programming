package com.example.ntvid.timemanager.dummy;

import com.example.ntvid.timemanager.model.Task;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ntvid on 08/11/2017.
 */

public  class  DummyList {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Task> hardCoded = Arrays.asList( new Task(1l,"Hard1","moreHardcode","location",format.format(new Date())
            ),new Task(2l,"Hard2","moreHardcode","location",format.format(new Date())),
            new Task(3l,"Hard3","moreHardcode","location",format.format(new Date())));;
}
