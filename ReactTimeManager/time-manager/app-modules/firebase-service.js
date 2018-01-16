import * as firebase from "firebase";
import {AsyncStorage} from "react-native";

class FirebaseService {
    constructor() {
        this.state = {
            tasks: []
        }
    }

    static instance = null

    static getInstance() {

        if (FirebaseService.instance !== null) {
            return this.instance;
        } else {
            var config = {
                apiKey: "AIzaSyDpFfQk-oJqwdKTlumUQvf4nMUcFtSc8GA",
                authDomain: "mobile-d30f3.firebaseapp.com",
                databaseURL: "https://mobile-d30f3.firebaseio.com",
                projectId: "mobile-d30f3",
                storageBucket: "mobile-d30f3.appspot.com",
                messagingSenderId: "314170306557"
            };
            firebase.initializeApp(config);
            this.instance = new FirebaseService();

            var tasksRef = this.instance.getRef("tasks");
            tasksRef.on('child_added', function (data) {
                /*this.state.tasks.push({
                    id: data.key,
                    name: data.val().name,
                    description: data.val().description,
                    location: data.val().location,
                    deadline: data.val().deadline
                });*/
            });



            tasksRef.on('child_changed', function(data) {

            });


            tasksRef.on('child_removed', function (data) {

            });
        }
    }


    getRef(refName) {
        return firebase.database().ref(refName);
    }

    addTask(task) {
        let ref = this.getRef("tasks")
        let id = ref.push().getKey();
        task.id = id
        ref.child(id).setValue(task)
    }

    deleteTask(id) {
        let ref = this.getRef("tasks")
        ref.child(id).setValue(task)
    }

    pdateTask(task) {
        let ref = this.getRef("tasks")
        ref.child(task.id).setValue(task)
    }




}

export default FirebaseService
