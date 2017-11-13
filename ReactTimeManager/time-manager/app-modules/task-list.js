import React,{ Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View,ListView,Text,TouchableHighlight, Navigator} from  'react-native'
import { Actions } from 'react-native-router-flux';
import Tasks from '../global.js'




export default class TaskList extends Component {
    constructor(props) {

        super(props);
        let ds = new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2,
        });
        this.state = {


            dataSource: ds.cloneWithRows(Tasks.getTasks()),
            db: Tasks.getTasks()
        }



    }

    _goToList(){
        Actions.list()
    }

    static update(){
        this.setState({
            dataSource: ds.cloneWithRows(Tasks.getTasks()),
            db: Tasks.getTasks()
        })
    }
    _renderTask(task){
       var obj = {name: task.name}
        return(
            <TouchableHighlight style={styles.cell}
                                onPress={function () {
                                    Actions.detail({text: task.name, text1: task.description, text2:task.location,
                                    text3: task.deadline, text0: task.id.toString(), action: this.update });
                                }
                                }>
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