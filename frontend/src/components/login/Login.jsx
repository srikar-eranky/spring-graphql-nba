import React from "react";
import { signInWithGoogle } from "../../firebase/firebaseConfig";
import { useNavigate } from "react-router-dom";

const Login = () => {

    const navigate  = useNavigate();

    const handleLogin = async () => {
        try {
            const user = await signInWithGoogle();
            console.log("Welcome", user.displayName);
            navigate("/stats");
        } catch (error) {
            console.error(error);
        }
    }
    return (
        <>
            <button onClick={handleLogin}>Login</button>
        </>
    );
}

export default Login;