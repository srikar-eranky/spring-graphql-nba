import React from "react";
import { doSignOut } from "../../firebase/firebaseConfig";
import { useNavigate } from "react-router-dom";

const LogOut = () => {
    const navigate = useNavigate();
    const handleSignOut = () => {
        try {
            const result = doSignOut();
            navigate('/');
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