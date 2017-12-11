import React, {Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View, ListView, Text, TouchableHighlight, Navigator} from 'react-native'
import {Actions} from 'react-native-router-flux';
import Tasks from '../global.js'


export default class TaskList extends Component {
    constructor(props) {

        super(props);
        this.state = {
            isLoading: true,
            tasks: Tasks.getTasks(),
            dataSource: undefined,
            db: undefined
        }
        this._deleteAction = this._deleteAction.bind(this);
    }

    componentWillMount() {
        let ds = new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2,
        });
        this.state.tasks.then(results => {
            this.setState({

                dataSource: ds.cloneWithRows(results),
                db: results,
                isLoading: false,
            })
        })
    }

    _goToList() {
        Actions.list()
    }

    _deleteAction(task) {
        Tasks.deleteTask(task).then((t) => {
            Actions.list()
        })
    }

    static update() {
        this.setState({
            dataSource: ds.cloneWithRows(Tasks.getTasks()),
            db: Tasks.getTasks()
        })
    }

    _renderTask(task) {
        var obj = {name: task.name}
        return (
            <View>
                <TouchableHighlight style={styles.cell}
                                    onPress={function () {
                                        Actions.detail({
                                            task_name: task.id, text1: task.description, text2: task.location,
                                            text3: task.deadline, action: this.update
                                        });
                                    }
                                    }>
                    <Text>{task.name}</Text>
                </TouchableHighlight>

            </View>
        )
    }

    render() {
        if (this.state.isLoading) {
            return <View><Text>Loading...</Text></View>
        } else
            return (
                <View style={styles.layout}>

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