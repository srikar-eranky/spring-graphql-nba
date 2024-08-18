import React from "react";
import { doSignOut } from "../../firebase/firebaseConfig";

const LogOut = () => {
    const handleSignOut = () => {
        try {
            const result = doSignOut();
        } catch (error) {
            console.error(error);
        }
        
    }
    return (
        <>
            <button onClick={handleSignOut}>Log Out</button>
        </>
    )
}

export default LogOut;