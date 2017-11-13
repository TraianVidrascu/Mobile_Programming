import React,{ Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View,TextInput,Text, Linking} from  'react-native';
import Tasks from '../global';
import { Actions } from 'react-native-router-flux';
import TaskList from "./task-list";

export default class TaskDetail extends Component{
    constructor(props){
        super(props);
        this.state = {
            id: props.text0,
            name : Tasks.getTasks()[props.text0].name,
            description: Tasks.getTasks()[props.text0].description,
            location: Tasks.getTasks()[props.text0].location,
            deadline: Tasks.getTasks()[props.text0].deadline,
            action: props.action

        };
        this._goToList = this._goToList.bind(this)
    }
    _update(task){
        Tasks.setTask(task)
        Actions.list();
    }
    _goToList(){
        Actions.list()
    }


    render(){
        return(
        <View style={styles.layout}>
            <View style={styles.container}>
                <Text>Name:</Text>
                <TextInput style={styles.textInput}
                    value = {this.state.name}
                           onChangeText = {(text) => {this.setState({name:text});
                                            }}
                />
            </View>
            <View style={styles.container}>
                <Text>Description:</Text>
                <TextInput style={styles.textInput}
                           multiline = {true}
                           numberOfLines = {4}
                           value = {this.state.description}
                           onChangeText = {(text) => this.setState({description:text})}
                />
            </View>
            <View style={styles.container}>
                <Text>Location:</Text>
                <TextInput style={styles.textInput}
                           value = {this.state.location}
                           onChangeText = {(text) => this.setState({location:text})}
                />
            </View>
            <View style={styles.container}>
                <Text>Deadline:</Text>
                <TextInput style={styles.textInput}
                           value = {this.state.deadline}
                           onChangeText = {(text) => this.setState({deadline:text})}
                />
            </View>
            <View style={styles.button}>
                <Button
                    title ='Update'
                    onPress={()=>this._update({
                            name : this.state.name,
                            description: this.state.description,
                            location: this.state.location,
                            deadline: this.state.deadline,
                            id: this.state.id
                        })
                      }/>
            </View>
        </View>
        )

    }
}

const styles = StyleSheet.create({
    button: {
        margin:20,
    },
    container: {
    },
    layout: {
        paddingTop: 20,
        margin:20,
        justifyContent:'space-between',
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