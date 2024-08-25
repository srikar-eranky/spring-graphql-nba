import React from "react";

const TeamCard = ({ team }) => {
    return (
        <div style={{"border": "1px solid black"}}>
            <p>Id: {team.id}</p>
            <p>Name: {team.name}</p>
            <p>City: {team.city}</p>
            <p>Conference: {team.conference}</p>
        </div>
    )
}

export default TeamCard;