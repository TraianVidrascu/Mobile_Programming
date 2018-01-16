import React, {Component} from 'react';
import {
    Alert, AppRegistry, Button, StyleSheet, View, ListView, Text, TouchableHighlight, Navigator,
    ActivityIndicator
} from 'react-native'
import {Actions} from 'react-native-router-flux';
import Tasks from '../global.js'
import * as firebase from "firebase";


export default class TaskList extends Component {
    constructor(props) {

        super(props);
        this.state = {
            isAdmin: false,
            tasksRef: firebase.database().ref('tasks'),
            isLoading: true,
            tasks: Tasks.getTasks(),
            dataSource: new ListView.DataSource({
                rowHasChanged: (row1, row2) => row1 !== row2,
            }),
            db: undefined
        }

        this._deleteAction = this._deleteAction.bind(this);
        this.listenForItems = this.listenForItems.bind(this);
        this._isAdmin = this._isAdmin.bind(this);

    }

    listenForItems(itemsRef) {
        this.state.tasksRef.on('value', (snap) => {

            // get children as an array
            var items = [];
            snap.forEach((child) => {
                items.push({
                    name: child.val().name,
                    description: child.val().description,
                    location: child.val().location,
                    deadline: child.val().deadline,
                    id: child.key
                });
            });
            this.setState({
                dataSource: this.state.dataSource.cloneWithRows(items),
                isLoading: false
            });

        });
    }
    _isAdmin(){
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

    componentDidMount() {
        this.listenForItems(this.itemsRef);
        this._isAdmin();


    }

    _goToList() {
        Actions.list()
    }

    _deleteAction(task) {
        Tasks.deleteTask(task).then((t) => {
            Actions.list()
        })
    }


    _renderTask(task) {
        var obj = {name: task.name}
        return (
            <View>
                <TouchableHighlight style={styles.cell}
                                    onPress={function () {
                                        Actions.detail({
                                            task_id: task.id,
                                            task_name: task.name,
                                            task_description: task.description,
                                            task_location: task.location,
                                            task_deadline: task.deadline
                                        });
                                    }
                                    }>
                    <Text>{task.name}</Text>
                </TouchableHighlight>

            </View>
        )
    }

    render() {
        const data = [[0, 1], [1, 3]];
        if (this.state.isAdmin) {
            return <View style={styles.layout}>

                <ListView style={styles.layout}
                          dataSource={this.state.dataSource}
                          renderRow={this._renderTask}
                />

                <View>
                    <Button
                        title='Add Task'
                        onPress={() => Actions.add()}
                    />
                </View>
            </View>
        } else
            return (
                <View style={styles.layout}>

                    <ListView style={styles.layout}
                              dataSource={this.state.dataSource}
                              renderRow={this._renderTask}
                    />
                </View>

            )
    }
}

const styles = StyleSheet.create({
    cell: {
        margin: 20,
        flex: 1,
        borderWidth: 1,
        borderColor: 'black'

    },
    button: {
        margin: 20,
    },
    container: {},
    layout: {
        margin: 20,
        flex: 1
    },
    textInput: {

        margin: 20,
        alignSelf: 'stretch',
        borderColor: 'gray'
    },
    password: {}
})