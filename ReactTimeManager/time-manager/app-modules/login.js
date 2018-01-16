import React,{ Component} from 'react';
import {Alert,StyleSheet,Button, View, TextInput, Text} from 'react-native'
import * as firebase from "firebase";

import {Actions} from 'react-native-router-flux';

export  default class Login extends Component {
    constructor(props){
        super(props);
        this.state = {
            email: 'Email',
            password: '',
        }
        this._onPressLogin = this._onPressLogin.bind(this)
        this._onPressRegister = this._onPressRegister.bind(this)
    }

    _onPressLogin(){
        const { email, password } = this.state;
        Actions.list()
        /*firebase.auth().signInWithEmailAndPassword(email, password)
            .then(() => {
                Actions.list()
            })
            .catch(() => {
                Alert.alert(
                    "Login failed!",
                    ""
                )
            });*/

    }

    componentWillMount() {

    }

    _onPressRegister(){
        Actions.register()
    }

    render(){
        return(
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
                        <Text>Pass</Text>
                        <TextInput
                            style = {styles.textInput}
                            onChangeText = {(text) => this.setState({password: text})}
                            value = {this.state.password}
                        />
                    </View>
                    <View style = {styles.container}>
                        <Button
                            style = {styles.buttons}
                            onPress={this._onPressLogin}
                            title="Login"
                        />
                    </View>
                    <View style = {styles.container}>
                        <Button
                            style = {styles.buttons}
                            onPress={this._onPressRegister}
                            title="Register"
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
