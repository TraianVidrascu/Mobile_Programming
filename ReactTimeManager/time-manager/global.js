import React,{ Component} from 'react';
import {Alert} from 'react-native'

class Tasks {
    static tasks =[
        {id:0, name:'task1', description:'description1',location:'cluj',deadline:'10-11-17'},
        {id:1, name:'task2', description:'description2',location:'cluj',deadline:'10-11-17'},
        {id:2, name:'task3', description:'description3',location:'cluj',deadline:'10-11-17'},
        {id:3, name:'task4', description:'description4',location:'cluj',deadline:'10-11-17'}
    ];

    static setTask(task){
        //toDo parse by id
        this.tasks[task.id] = task;
        alert(this.tasks[task.id].name)
    }

    static getTasks(){
        return this.tasks;
    }
}

export default Tasks
