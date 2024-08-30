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
        <div style={{ cursor: "pointer" }} onClick={handleSignOut}>
            <p>Log Out</p>
        </div>
    )
}

export default LogOut;