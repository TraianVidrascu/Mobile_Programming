import React,{ Component} from 'react';
import {Alert, AppRegistry, Button, StyleSheet, View,TextInput,Text, Linking} from  'react-native'

export default class TaskDetail extends Component{
    constructor(props){
        super(props);
        this.state = {
            name : props.text,
            description: props.text1,
            location: props.text2,
            deadline: props.text3,
            id: props.text0
        };
    }

    render(){

        return(
        <View style={styles.layout}>
            <View style={styles.container}>
                <Text>Name:</Text>
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