import * as firebase from "firebase";
import {AsyncStorage} from "react-native";
import Notification from 'react-native-system-notification';
class FirebaseService {
    constructor() {
        this.state = {
            tasks: []
        }
    }

    static userWathces = []
    static instance = null

    static getInstance() {

        if (FirebaseService.instance !== null) {
            return this.instance;
        } else {
            /*PushNotification.configure({
                onNotification: function (notification) {
                    console.log('NOTIFICATION:', notification);

                    // process the notification

                    // required on iOS only (see fetchCompletionHandler docs: https://facebook.github.io/react-native/docs/pushnotificationios.html)
                    notification.finish(PushNotificationIOS.FetchResult.NoData);
                },

                // Should the initial notification be popped automatically
                // default: true
                popInitialNotification: true,

                /!**
                 * (optional) default: true
                 * - Specified if permissions (ios) and token (android and ios) will requested or not,
                 * - if not, you must call PushNotificationsHandler.requestPermissions() later
                 *!/
                requestPermissions: true
            })*/
            var config = {
                apiKey: "AIzaSyDpFfQk-oJqwdKTlumUQvf4nMUcFtSc8GA",
                authDomain: "mobile-d30f3.firebaseapp.com",
                databaseURL: "https://mobile-d30f3.firebaseio.com",
                projectId: "mobile-d30f3",
                storageBucket: "mobile-d30f3.appspot.com",
                messagingSenderId: "314170306557"
            };
            firebase.initializeApp(config);
            /* firebase.firestore().enablePersistence()
                 .then(function() {
                     // Initialize Cloud Firestore through firebase
                     var db = firebase.firestore();
                 })
                 .catch(function(err) {
                     if (err.code == 'failed-precondition') {
                         // Multiple tabs open, persistence can only be enabled
                         // in one tab at a a time.
                         // ...
                     } else if (err.code == 'unimplemented') {
                         // The current browser does not support all of the
                         // features required to enable persistence
                         // ...
                     }
                 });*/
            this.instance = new FirebaseService();


            this.instance.getRef("wathces").on('value', (snap) => {

                // get children as an array

                snap.forEach((child) => {
                    FirebaseService.userWathces.push({
                        id: data.key,
                        uid: data.val().uid,
                        taskId: data.val().taskId
                    });
                });

            });


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


            tasksRef.on('child_changed', function (data) {
                console.log("notif")
                FirebaseService.userWathces.forEach(watch => {
                    if (watch.taskId === data.val().id) {
                        console.log(watch.taskId)
                        console.log(data.val().id)
                        var objRef = FirebaseService.instance.getRef("notifications").push();
                        objRef.set({id: objRef.key, uid: watch.uid, message: data.val().name + " was modified"})
                    }
                })
            });


            tasksRef.on('child_removed', function (data) {
                console.log("notif")
                FirebaseService.userWathces.forEach(watch => {
                    if (watch.taskId === data.val().id) {
                        var objRef = FirebaseService.instance.getRef("notifications").push();
                        objRef.set({id: objRef.key, uid: watch.uid, message: data.val().name + " was deleted"})
                    }
                })
            });

            var watchesRef = this.instance.getRef("watches");
            watchesRef.on('child_added', function (data) {
                FirebaseService.userWathces.push({
                    id: data.key,
                    uid: data.val().uid,
                    taskId: data.val().taskId
                });
            });


            watchesRef.on('child_changed', function (data) {

            });


            watchesRef.on('child_removed', function (data) {
                var filtred = FirebaseService.userWathces.filter(item => item.id === data.id)
                FirebaseService.userWathces = filtred;
            });

            var notificationsRef = this.instance.getRef("notifications");
            notificationsRef.on('child_added', function (data) {
                if (data.val().uid === firebase.auth().currentUser.uid) {
                   /* PushNotification.localNotification({
                        title: "My Notification Title", // (optional, for iOS this is only used in apple watch, the title will be the app name on other iOS devices)
                        message: data.val().message,
                    })*/
                    Notification.create({ subject: 'Task', message: data.val().message });
                    FirebaseService.instance.getRef('notifications').child(data.key).remove()
                }
            });


            notificationsRef.on('child_changed', function (data) {

            });


            notificationsRef.on('child_removed', function (data) {

            });
        }
    }

    deleteWatch(uid, tid) {
        FirebaseService.userWathces.forEach(watch => {
            if (watch.uid === uid && watch.taskId === tid) {
                FirebaseService.instance.getRef("watches").child(watch.id).remove()
            }
        });

    }

    getRef(refName) {
        return firebase.database().ref(refName);
    }


}

export default FirebaseService
