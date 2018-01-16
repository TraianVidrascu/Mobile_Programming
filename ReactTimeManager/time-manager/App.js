import React from 'react';
import RegisterButton from "./app-modules/register";
import TaskList from "./app-modules/task-list";
import TaskDetail from "./app-modules/task-detail";
import * as firebase from "firebase";

import { Router, Scene } from 'react-native-router-flux';
import TaskAdd from "./app-modules/task-add";
import Login from "./app-modules/login";



const App = (props) => {
    return (
        <Router>
            <Scene key="root">
                <Scene
                    key="login"
                    component={Login}
                    title="Login"
                    handleClick = {props.handleClick}
                />
                <Scene key="register"
                       component={RegisterButton}
                       title="Register"
                       handleClick = {props.handleClick}
                />
                <Scene
                    key="list"
                    component={TaskList}
                    title="Task List"
                    handleClick = {props.handleClick}
                />
                <Scene
                    key="detail"
                    component={TaskDetail}
                    title="Task Detail"
                    handleClick = {props.handleClick}
                />
                <Scene
                    key="add"
                    component={TaskAdd}
                    title="Task Add"
                    handleClick = {props.handleClick}
                />
            </Scene>

        </Router>
    );
}

export default App;
