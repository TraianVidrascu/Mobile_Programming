import React, {Component} from 'react';
import {Actions} from 'react-native-router-flux';

import {ActivityIndicator,Alert, AsyncStorage, Button, View, TextInput, Text} from 'react-native';
import Tasks from "../global";
import * as firebase from "firebase";

export default class TaskAdd extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tasksRef: firebase.database().ref('tasks'),
            id: undefined,
            name: undefined,
            description: undefined,
            location: undefined,
            deadline: undefined,
            isLoading: false,
        }

        this._addTask = this._addTask.bind(this)
    }



    _addTask(task) {
        try {
            if (this.state.name && this.state.description && this.state.location && this.state.deadline) {
                console.log(Tasks.generateId())
                var objRef  = this.state.tasksRef.push()
                objRef.set({
                    id: objRef.key,
                    name: task.name,
                    description: task.description,
                    location: task.location,
                    deadline: task.deadline,
                })
                Actions.list();
                /*AsyncStorage.getItem(task.id).then((value) => {
                    if (value != null) {
                        AsyncStorage.setItem(task.id, JSON.stringify(task)).then(() => {
                            console.log('success');
                            Alert.alert("task saved")
                            this.setStaet({
                                isLoading:true
                            })
                            Actions.list();
                        })
                    } else {
                        AsyncStorage.setItem(task.id, JSON.stringify(task)).then(() => {
                            console.log('good');
                            Alert.alert("task saved")
                            this.setStaet({
                                isLoading:true
                            })
                            Actions.list();
                        })
                    }
                    Actions.list();
                })*/
            }
        }
        catch (e) {
            console.log(e.message);
        }
    }
    /*chart: {
        width: 200,
        height: 200,
    };*/



    render() {
        /*const data = [
            [0,1],
        ];*/
        if (this.state.isLoading) {
            return <View><ActivityIndicator style="large"/></View>
        } else
            return (
                <View>
                    <Text>Add your task</Text>
                    <View>
                        <Text>Task name:</Text>
                        <TextInput onChangeText={(text) => {
                            this.setState({name: text});
                        }}/>
                    </View>
                    <View>
                        <Text>Task description:</Text>
                        <TextInput
                            onChangeText={(text) => {
                                this.setState({description: text});
                            }}
                            multiline={true}
                            numberOfLines={4}
                        />
                    </View>
                    <View>
                        <Text>Task location:</Text>
                        <TextInput
                            onChangeText={(text) => {
                                this.setState({location: text});
                            }}
                        />
                    </View>
                    <View>
                        <Text>Task deadline:</Text>
                        <TextInput
                            onChangeText={(text) => {
                                this.setState({deadline: text});
                            }}
                        />
                    </View>
                    <View>
                        <Button
                            title="Submit"
                            onPress={() => {
                                this._addTask(
                                    {
                                        id: this.state.id,
                                        name: this.state.name,
                                        description: this.state.description,
                                        location: this.state.location,
                                        deadline: this.state.deadline,
                                        isLoading:true
                                    }
                                )
                            }}/>
                    </View>
                    <View>
                        {/*<Chart
                            style={this.chart}
                            data={data}
                            verticalGridStep={5}
                            type="bar"
                        />*/}
                    </View>
                </View>
            )
    }
}