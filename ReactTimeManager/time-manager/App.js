import React from 'react';
import RegisterButton from "./app-modules/register";
import TaskList from "./app-modules/task-list";
import TaskDetail from "./app-modules/task-detail";

import { Router, Scene } from 'react-native-router-flux';


const App = (props) => {
    return (
        <Router>
            <Scene key="root">
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
            </Scene>
        </Router>
    );
}

export default App;
