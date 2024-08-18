// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { GoogleAuthProvider, getAuth, signInWithPopup } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: "spring-graphql-nba.firebaseapp.com",
  projectId: "spring-graphql-nba",
  storageBucket: "spring-graphql-nba.appspot.com",
  messagingSenderId: "1031809066513",
  appId: "1:1031809066513:web:0156dbe1dcb1f132e0b3b0",
  measurementId: "G-ZEYQ13SFPD"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

const signInWithGoogle = async () => {
  const provider = new GoogleAuthProvider();
  const result = await signInWithPopup(auth, provider);
  return result.user;
}

const doSignOut = () => {
  return auth.signOut();
}

export { app, auth, signInWithGoogle,
          doSignOut };