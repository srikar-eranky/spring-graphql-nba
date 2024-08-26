import React from "react";

const PlayerCard = ({ player, onClick }) => {
    return (
        <div style={{"border": "1px solid black", "cursor": "pointer"}} onClick={onClick}>
            <p>Player Id: {player.id}</p>
            <p>Player Name: {player.name}</p>
            <p>Player Age: {player.age}</p>
        </div>
    )
}

export default PlayerCard;