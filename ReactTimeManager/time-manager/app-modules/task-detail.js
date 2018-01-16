import React, {Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View, TextInput, Text, Linking, ActivityIndicator} from 'react-native';
import Tasks from '../global';
import {Actions} from 'react-native-router-flux';
import TaskList from "./task-list";
import * as firebase from "firebase";
import FirebaseService from "./firebase-service";

export default class TaskDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isAdmin: false,
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
        this._isAdmin = this._isAdmin.bind(this)
        this._follow = this._follow.bind(this)
        this._unfollow = this._unfollow.bind(this)
    }

    _isAdmin() {
        var isAdmin = false
        let ref = firebase.database().ref('admins');
        ref.on('value', (snap) => {
            snap.forEach((child) => {
                console.log(firebase.auth().currentUser.email)
                console.log(child.val().email)
                if (child.val().email === firebase.auth().currentUser.email) {
                    isAdmin = true;
                }
            })
        })
        this.setState({
            isAdmin: isAdmin
        })
    }

    componentWillMount() {
        this._isAdmin();
        this.setState({
            id: this.props.task_id,
            name: this.props.task_name,
            description: this.props.task_description,
            location: this.props.task_location,
            deadline: this.props.task_deadline,
            isLoading: false,
        })
    }

    _follow() {
        var watchRef =  firebase.database().ref('watches');
        objRef = watchRef.push()
        objRef.set({
            id: objRef.key,
            taskId: this.state.id,
            uid: firebase.auth().currentUser.uid
        })
    }

    _unfollow(){
        FirebaseService.getInstance().deleteWatch(firebase.auth().currentUser.uid,this.state.id)
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
        if (this.state.isAdmin) {
            return <View style={styles.layout}>
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
                <Button
                    title="Follow"
                    onPress={() => this._follow({
                    })}
                />
                <Button
                    title="Unfollow"
                    onPress={() => this._unfollow({
                    })}
                />
            </View>
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
                    <Button
                        title="Follow"
                        onPress={() => this._follow({
                        })}
                    />
                    <Button
                        title="Unfollow"
                        onPress={() => this._unfollow({
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