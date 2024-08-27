import React from "react";
import { useAuth } from "../../contexts/authContext";
import LogOut from "../logout/Logout";
import styles from './navbar.module.css';
import { useNavigate } from "react-router-dom";
import Login from "../login/Login";

const Navbar = () => {
    const { signedIn } = useAuth();
    const navigate = useNavigate();

    const handlePlayerClick = () => {
        if(signedIn) {
            navigate('/players/createPlayer');
        } else {
            navigate('/');
        }
    }

    const handleTeamClick = () => {
        if (signedIn) {
            navigate('/teams/createTeam');
        } else {
            navigate('/');
        }
    }

    const handleHome = () => {
        navigate('/');
    }
    return (
        <div className={styles.navbar}>
            <div className={styles.navElement} onClick={handleHome}>
                <p>Home</p>
            </div>
            <div className={styles.navElement} onClick={handlePlayerClick}>
                <p>Create Player</p>
            </div>
            <div className={styles.navElement} onClick={handleTeamClick}>
                <p>Create Team</p>
            </div>
            {signedIn ? (
                <div className={styles.navElement}>
                    <LogOut />
                </div>
            ) : (
                <div className={styles.navElement}>
                    <Login />
                </div>
            )}
        </div>
    )
}

export default Navbar;