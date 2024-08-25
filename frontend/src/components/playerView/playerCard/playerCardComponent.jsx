import React from "react";

const PlayerCard = ({ player }) => {
    return (
        <div style={{"border": "1px solid black"}}>
            <p>Player Id: {player.id}</p>
            <p>Player Name: {player.name}</p>
            <p>Player Age: {player.age}</p>
        </div>
    )
}

export default PlayerCard;