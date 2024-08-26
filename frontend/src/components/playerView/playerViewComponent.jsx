import React from "react";
import PlayerCard from "./playerCard/playerCardComponent";
import { useQuery, gql } from "@apollo/client";
import { useNavigate } from "react-router-dom";

const PlayerViewComponent = () => {

    const navigate = useNavigate();

    const GET_PLAYERS = gql`
    {
        players {
            id
            teamId
            name
            age
        }
    }
    `

    const { loading, error, data } = useQuery(GET_PLAYERS);

    if(loading) return (<>Loading...</>)
    if (error) return <p>Error: {error.message}</p>;

    const playersData = data.players;

    const handlePlayerClick = (playerId) => {
        return () => {
            navigate(`/players/${playerId}`);
        }
    }
    
    return (
        <>
            <h1>Player List</h1>
            {playersData.map((player) => (
                <div key={player.id}>
                    <PlayerCard player={player} onClick={handlePlayerClick(playerId)}/>
                </div>
            ))}
        </>
    )
}

export default PlayerViewComponent;