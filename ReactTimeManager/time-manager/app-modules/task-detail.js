import React,{ Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View,TextInput,Text, Linking} from  'react-native'

export default class TaskDetail extends Component{
    constructor(props){
        super(props);
        this.state = {
            id: props.id,
            name : props.name,
            description : props.description,
            location: props.location,
            deadline : props.deadline
        };
        alert(props.id+this.state.name+this.state.deadline)

    }

    render(){

        return(
        <View style={styles.layout}>
            <Text>Name:</Text>
            <View style={styles.container}>
                <TextInput style={styles.textInput}
                    value = {this.state.name}
                           onChangeText = {(text) => this.setState({name:text})}
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