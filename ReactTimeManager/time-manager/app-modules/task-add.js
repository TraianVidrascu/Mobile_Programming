import React, {Component} from 'react';
import {Actions} from 'react-native-router-flux';

import {Alert, AsyncStorage, Button, View, TextInput, Text} from 'react-native';
import Tasks from "../global";

export default class TaskAdd extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: undefined,
            name: undefined,
            description: undefined,
            location: undefined,
            deadline: undefined,
            isLoading: false,
        }

        this._addTask = this._addTask.bind(this)
    }

    /*componentWillMount() {
        AsyncStorage.getAllKeys().map((list,index)=>{

        }).then()e
        Tasks.generateId().then(id => {
            this.setState({
                id: id,
                isLoading: false,
            })
        })
    }*/

    _addTask(task) {
        try {
            if (this.state.name && this.state.description && this.state.location && this.state.deadline) {
                console.log(Tasks.generateId())
                AsyncStorage.getItem(task.id).then((value) => {
                    if (value != null) {
                        AsyncStorage.setItem(task.id, JSON.stringify(task)).then(() => {
                            console.log('success');
                            Alert.alert("task saved")
                            Actions.list();
                        })
                    } else {
                        AsyncStorage.setItem(task.id, JSON.stringify(task)).then(() => {
                            console.log('good');
                            Alert.alert("task saved")
                            Actions.list();
                        })
                    }
                })
            }
        }
        catch (e) {
            console.log(e.message);
        }
    }

    render() {
        if (this.state.isLoading) {
            return <View><Text>Loading...</Text></View>
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
                                        id: this.state.name,
                                        name: this.state.name,
                                        description: this.state.description,
                                        location: this.state.location,
                                        deadline: this.state.deadline,
                                    }
                                )
                            }}/>
                    </View>
                </View>
            )
    }
}