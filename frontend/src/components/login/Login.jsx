import React from "react";
import { signInWithGoogle } from "../../firebase/firebaseConfig";
import { useNavigate } from "react-router-dom";

const Login = ({ setUserClicked }) => {

    const navigate  = useNavigate();

    const handleLogin = async () => {
        setUserClicked(false);
        try {
            const user = await signInWithGoogle();
            console.log("Welcome", user.displayName);
            navigate("/players/createPlayer");
        } catch (error) {
            console.error(error);
        }
    }
    return (
        <div onClick={handleLogin}>
            <p>Login</p>
        </div>
    );
}

export default Login;