import React from "react";
import styles from './playerCardComponent.module.css';

const PlayerCard = ({ player, onClick }) => {
    return (
        <div className={styles.card} onClick={onClick}>
            <p><b>Player Id:</b> {player.id}</p>
            <p><b>Player Name:</b> {player.name}</p>
            <p><b>Player Age:</b> {player.age}</p>
        </div>
    )
}

export default PlayerCard;