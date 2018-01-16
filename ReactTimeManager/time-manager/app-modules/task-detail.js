import React, {Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View, TextInput, Text, Linking, ActivityIndicator} from 'react-native';
import Tasks from '../global';
import {Actions} from 'react-native-router-flux';
import TaskList from "./task-list";
import * as firebase from "firebase";

export default class TaskDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: undefined,
            tasksRef: firebase.database().ref('tasks'),
            task: {},
            isLoading: true,
            name: undefined,
            description: undefined,
            location: undefined,
            deadline: undefined,
            action: props.action

        };
        this._goToList = this._goToList.bind(this)
        this._delete = this._delete.bind(this)
    }

    componentWillMount() {
        this.setState({
            id: this.props.task_id,
            name: this.props.task_name,
            description: this.props.task_description,
            location: this.props.task_location,
            deadline: this.props.task_deadline,
            isLoading: false,
        })
    }


    _update(task) {
        this.state.tasksRef.child(this.state.id).update({
            id: this.state.id,
            name: this.state.name,
            description: this.state.description,
            location: this.state.location,
            deadline: this.state.deadline,
        })
        Alert.alert("task updated")
    }

    _delete(task) {
        this.state.tasksRef.child(this.state.id).remove();
        Alert.alert("task removed")
        Actions.list()

    }

    _goToList() {
        Actions.list()
    }


    render() {
        if (this.state.isLoading) {
            return <View><ActivityIndicator style="large"/></View>
        } else
            return (
                <View style={styles.layout}>
                    <View style={styles.container}>
                        <Text>Name:</Text>
                        <TextInput style={styles.textInput}
                                   value={this.state.name}
                                   onChangeText={(text) => {
                                       this.setState({name: text});
                                   }}
                        />
                    </View>
                    <View style={styles.container}>
                        <Text>Description:</Text>
                        <TextInput style={styles.textInput}
                                   multiline={true}
                                   numberOfLines={4}
                                   value={this.state.description}
                                   onChangeText={(text) => this.setState({description: text})}
                        />
                    </View>
                    <View style={styles.container}>
                        <Text>Location:</Text>
                        <TextInput style={styles.textInput}
                                   value={this.state.location}
                                   onChangeText={(text) => this.setState({location: text})}
                        />
                    </View>
                    <View style={styles.container}>
                        <Text>Deadline:</Text>
                        <TextInput style={styles.textInput}
                                   value={this.state.deadline}
                                   onChangeText={(text) => this.setState({deadline: text})}
                        />
                    </View>
                    <View style={styles.button}>
                        <Button
                            title='Update'
                            onPress={() => this._update({
                                name: this.state.name,
                                description: this.state.description,
                                location: this.state.location,
                                deadline: this.state.deadline,
                                id: this.state.id
                            })
                            }/>
                    </View>
                    <Button
                        title='DELETE'
                        onPress={() => this._delete({
                            id: this.state.id
                        })}
                    />
                </View>
            )

    }
}

const styles = StyleSheet.create({
    button: {
        margin: 20,
    },
    container: {},
    layout: {
        paddingTop: 20,
        margin: 20,
        justifyContent: 'space-between',
        flex: 1
    },
    textInput: {

        margin: 20,
        alignSelf: 'stretch',
        borderColor: 'gray'
    },
    password: {}
})