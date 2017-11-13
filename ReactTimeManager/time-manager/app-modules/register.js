import React,{ Component} from 'react';

import {Alert, AppRegistry, Button, StyleSheet, View,TextInput,Text, Linking} from  'react-native'
import { Actions } from 'react-native-router-flux';

export  default class RegisterButton extends Component{
    constructor(props){
        super(props);
        this.state = {
            fullName: 'Full Name',
            username: 'User name',
            password: '',
        };
        this._onPressRegister = this._onPressRegister.bind(this)
        this._goToList = this._goToList.bind(this)
    }

    _goToList(){
        Actions.list()
    }

    _onPressRegister(){
        Linking.openURL('mailto:ntvidrascu@gmail.com?subject=register&body= hi '+this.state.fullName+'\n'
        +'username: '+this.state.username+'\n'
        +'passowrd: '+this.state.password);
        Alert.alert('verify your mail');
    }


    render(){

        return (
            <View style = {styles.layout}>

                <View style = {styles.container}>
                    <Text>Full Name</Text>
                    <TextInput
                        style = {styles.textInput}
                        onChangeText = {(text) => this.setState({fullName: text})}
                        value = {this.state.fullName}
                    />
                </View>
                <View style = {styles.container}>
                    <Text>Username</Text>
                    <TextInput
                        style = {styles.textInput}
                        onChangeText = {(text) => this.setState({username: text})}
                        value = {this.state.username}
                    />
                </View>
                <View style = {styles.container}>
                    <Text>Password</Text>
                    <TextInput
                        style = {styles.textInput}
                        onChangeText = {(text) => this.setState({password: text})}
                        value = {this.state.password}
                        secureTextEntry = {true}
                    />
                </View>
                <View style = {styles.container}>
                    <Button
                        style = {styles.buttons}
                        onPress={this._onPressRegister}
                        title="Register"
                    />
                </View>
                <View style = {styles.container}>
                    <Button
                        style = {styles.buttons}
                        onPress={this._goToList}
                        title="ViewList"
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

