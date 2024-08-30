import React, { useState } from "react";
import { useAuth } from "../../contexts/authContext";
import LogOut from "../logout/Logout";
import styles from './navbar.module.css';
import { useNavigate } from "react-router-dom";
import Login from "../login/Login";

const Navbar = () => {
    const { currentUser, signedIn } = useAuth();
    const navigate = useNavigate();
    const [userClicked, setUserClicked] = useState(false);

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

    const handleUserClicked = (clicked) => {
        setUserClicked(clicked);
    }

    const handleHome = () => {
        navigate('/');
    }
    return (
        <div className={styles.navbar}>
            <div className={styles.navElement} onClick={handleHome}>
                <p>Home</p>
            </div>
            {signedIn && (
                <>
                    <div className={styles.navElement} onClick={handlePlayerClick}>
                        <p>Create Player</p>
                    </div>
                    <div className={styles.navElement} onClick={handleTeamClick}>
                        <p>Create Team</p>
                    </div>
                </>
            )}
            {signedIn && currentUser ? (
                <div style={{ display: "flex", flexDirection: "column" }}>
                    <div className={styles.navElement}>
                        <p onClick={() => setUserClicked(!userClicked)}>{currentUser.displayName} <span style={!userClicked ? { display: "inline-block", transform: "rotate(180deg)"} : { display: "inline-block" }}>^</span></p>
                        {userClicked && (
                        <LogOut />
                        )}
                    </div>
                    
                </div>
            ) : (
                <div className={styles.navElement}>
                    <Login setUserClicked={handleUserClicked}/>
                </div>
            )}
        </div>
    )
}

export default Navbar;