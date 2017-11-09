import React,{ Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View,ListView,Text,TouchableHighlight, Navigator} from  'react-native'
import { Actions } from 'react-native-router-flux';


const tasks = [
    {id:1, name:'task1', description:'description1',location:'cluj',deadline:'10-11-17'},
    {id:2, name:'task2', description:'description2',location:'cluj',deadline:'10-11-17'},
    {id:3, name:'task3', description:'description3',location:'cluj',deadline:'10-11-17'},
    {id:4, name:'task4', description:'description4',location:'cluj',deadline:'10-11-17'},
]

export default class TaskList extends Component {
    constructor(props) {
        super(props);
        let ds = new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2,
        });
        this.state = {

            dataSource: ds.cloneWithRows(tasks),
            db: tasks
        }
    }


    _renderTask(task){
        return(
            <TouchableHighlight style={styles.cell}
                                onPress={function (task) {
                                    Actions.detail({
                                        id: task.id,
                                        name : task.name,
                                        description : task.description,
                                        location: task.location,
                                        deadline : task.deadline
                                    });
                                }}>
                <Text>{task.name}</Text>
            </TouchableHighlight>
        )
    }
    render(){
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
    cell:{
        margin:20,
        flex:1,
        borderWidth:1,
        borderColor:'black'

    },
    button: {
        margin:20,
    },
    container: {
    },
    layout: {
        margin:20,
        flex:1
    },
    textInput: {

        margin: 20,
        alignSelf: 'stretch',
        borderColor: 'gray'
    },
    password: {

    }
})