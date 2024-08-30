import React from "react";
import styles from './teamCardComponent.module.css';

const TeamCard = ({ team, onClick }) => {
    return (
        <div className={styles.teamCard} onClick={onClick}>
            <p>Id: {team.id}</p>
            <p>Name: {team.name}</p>
            <p>City: {team.city}</p>
            <p>Conference: {team.conference}</p>
        </div>
    )
}

export default TeamCard;