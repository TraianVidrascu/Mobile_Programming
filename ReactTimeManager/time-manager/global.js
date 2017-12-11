import React, {Component} from 'react';
import {Alert, AsyncStorage} from 'react-native'
import uniqueId from 'react-native-unique-id'

class Tasks {
    static tasks = [
        {id: 0, name: 'task1', description: 'description1', location: 'cluj', deadline: '10-11-17'},
        {id: 1, name: 'task2', description: 'description2', location: 'cluj', deadline: '10-11-17'},
        {id: 2, name: 'task3', description: 'description3', location: 'cluj', deadline: '10-11-17'},
        {id: 3, name: 'task4', description: 'description4', location: 'cluj', deadline: '10-11-17'}
    ];

    static setTask(task) {
        return AsyncStorage.setItem(task.id, JSON.stringify(task))
    }

    static _parseResults(results) {
        const tasks = [];
        results.forEach(result => tasks.push(JSON.parse(result[1])));
        return tasks
    }

    static async getTasks() {
        try {
            return await AsyncStorage.getAllKeys().then(keys => {
                return AsyncStorage.multiGet(keys).then(results => {
                    return Tasks._parseResults(results);
                });

            });
        } catch (e) {

        }

    }

    static getTask(name) {
        return AsyncStorage.getItem(name).then(result => {
            return JSON.parse(result);
        })
    }
    static deleteTask(name){
        return AsyncStorage.removeItem(name.id)
    }
    static generateId() {
        /*let newKey = 1;
        return AsyncStorage.getAllKeys().map((list,index)=>{
            newKey+=1;
        })
        return newKey;*/
        return '1'
    }

}


export default Tasks
