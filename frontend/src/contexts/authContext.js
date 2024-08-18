import React, { useContext, useEffect, useState } from "react";
import { onAuthStateChanged } from "firebase/auth";
import { auth } from "../firebase/firebaseConfig";
import Loading from "../components/loading/Loading";

export const AuthContext = React.createContext();

export const useAuth = () => {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState(null);
    const [signedIn, setSignedIn] = useState(false);
    const [loading, setLoading] = useState(true);

    const initializeUser = (user) => {
        setLoading(true);
        if(user) {
            setCurrentUser(user);
            setSignedIn(true);
        } else {
            setCurrentUser(null);
            setSignedIn(false);
        }
        setLoading(false);
    }

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, initializeUser)
        return unsubscribe;
    })

    const value = {
        currentUser: currentUser,
        signedIn: signedIn,
        loading: loading
    }

    if(loading) {
        return <Loading />
    }

    return <AuthContext.Provider value={value}>
        {!loading && children}
    </AuthContext.Provider>
}