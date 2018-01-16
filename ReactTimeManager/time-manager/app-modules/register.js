import React,{ Component} from 'react';

import {Alert, AppRegistry, Button, StyleSheet, View,TextInput,Text, Linking} from  'react-native'
import { Actions } from 'react-native-router-flux';
import * as firebase from "firebase";

export  default class RegisterButton extends Component{
    constructor(props){
        super(props);
        this.state = {
            email: 'email',
            password: '',
        };
        this._onPressRegister = this._onPressRegister.bind(this);
        this._goToList = this._goToList.bind(this);
    }

    _goToList(){
        Linking.openURL('mailto:'+this.state.email+'?subject=register&body= hi '+this.state.fullName+'\n'
            +'username: '+this.state.username+'\n'
            +'passowrd: '+this.state.password);
        Alert.alert('verify your mail');

    }


    _onPressRegister(){
        const { email, password } = this.state;
        firebase.auth().createUserWithEmailAndPassword(email, password)
            .then(() => {
                let role = this.state.switchValue === false ? "user" : "admin"
                Actions.list()

            })
            .catch(() => {
                Alert.alert(
                    "Register failed!",
                    ""
                )
            });

    }


    render(){

        return (
            <View style = {styles.layout}>

                <View style = {styles.container}>
                    <Text>Email</Text>
                    <TextInput
                        style = {styles.textInput}
                        onChangeText = {(text) => this.setState({email: text})}
                        value = {this.state.email}
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
                        title="Mail"
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

