import React, { useEffect, useState } from "react";
import styles from './playerPage.module.css';
import { useParams } from "react-router-dom";
import { useApolloClient, gql } from "@apollo/client";
import Loading from "../../../components/loading/Loading";

const PlayerPage = () => {
    const { playerId } = useParams();
    const client = useApolloClient();
    const [player, setPlayer] = useState(null);
    const [stats, setStats] = useState(null);
    const [optionToggle, setOptionToggle] = useState("profile");

    const GET_PLAYER = gql`
        query GetPlayer($playerId: ID!){
            playerByPlayerId(id: $playerId) {
                id
                name
                age
                height
                position
            }
        }`;

    const GET_PLAYER_STATS = gql`
        query GetPlayerStats($playerId: ID!) {
            getPlayerStats(playerId: $playerId) {
                ppg
                apg
                rpg
            }
        }`;

    const getPlayerDetails = async () => {
        try {
            const { data } = await client.query({
                query: GET_PLAYER,
                variables: {
                    playerId: playerId
                }
            });
            const playerData = data.playerByPlayerId;
            setPlayer(playerData);
        } catch (error) {
            console.error(error);
        }
    }

    const getPlayerStats = async () => {
        try {
            const { data } = await client.query({
                query: GET_PLAYER_STATS,
                variables: {
                    playerId: playerId
                }
            });
            const statsData = data.getPlayerStats;
            setStats(statsData);   
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        getPlayerDetails();
        getPlayerStats();
    }, []);

    const handleProfileClick = () => {
        setOptionToggle("profile");
    }

    const handleStatsClick = () => {
        setOptionToggle("stats");
    }

    return (
        <div>
            {player ? (
                <>
                    <h1>{player.name}</h1>
                    <div className={styles.toggle}>
                        <button onClick={handleProfileClick}>Profile</button>
                        <button onClick={handleStatsClick}>Stats</button>
                    </div>
                    {optionToggle === "profile" && (
                        <>
                            <p>Age: {player.age}</p>
                            <p>Height: {player.height}</p>
                            <p>Position: {player.position}</p>
                        </>
                    )}
                    {optionToggle === "stats" && (
                        <>
                            <h1>Player Stats:</h1>
                            <table>
                                <tbody>
                                    <tr>
                                        <th>PPG</th>
                                        <th>APG</th>
                                        <th>RPG</th>
                                    </tr>
                                    {stats.map((season, index) => (
                                        <tr key={index}>
                                            <td>{season.ppg.toFixed(2)}</td>
                                            <td>{season.apg.toFixed(2)}</td>
                                            <td>{season.rpg.toFixed(2)}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </>    
                    )}
                </>
            ) : (
                <Loading />
            )}
        </div>
    )
}

export default PlayerPage;