import React from "react";

const TeamCard = ({ team, onClick }) => {
    return (
        <div style={{"border": "1px solid black", "cursor": "pointer"}} onClick={onClick}>
            <p>Id: {team.id}</p>
            <p>Name: {team.name}</p>
            <p>City: {team.city}</p>
            <p>Conference: {team.conference}</p>
        </div>
    )
}

export default TeamCard;