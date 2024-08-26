import React, { useEffect, useState } from "react";
import styles from './teamPage.module.css';
import { useNavigate, useParams } from "react-router-dom";
import { useApolloClient, gql } from "@apollo/client";
import PlayerCard from "../../components/playerView/playerCard/playerCardComponent";
import Loading from "../../components/loading/Loading";

const TeamPage = () => {
    const { teamId } = useParams();
    const client = useApolloClient();
    const navigate = useNavigate();
    const [players, setPlayers] = useState([]);
    const [teamStats, setTeamStats] = useState([]);
    const [teamOptionToggle, setTeamOptionToggle] = useState("players");

    const GET_PLAYERS_ON_TEAM = gql`
        query GetPlayersOnTeam($teamId: ID!) {
            playersByTeamId(teamId: $teamId) {
                id
                name
                age
                position
            }
        }`;
    
    const GET_TEAM_STATS = gql`
        query GetTeamStats($teamId: ID!) {
            getTeamStats(teamId: $teamId) {
                teamId
                ppg
                rpg
                apg
                season
                confRank
            }
        }`;

    const getPlayersOnTeam = async () => {
        try {
            const { data } = await client.query({
                query: GET_PLAYERS_ON_TEAM,
                variables: {
                    teamId: parseInt(teamId)
                }
            })
            const playersOnTeam = data.playersByTeamId;
            setPlayers(playersOnTeam);
        } catch (error) {
            console.error(error);
        }
    }

    const getTeamStats = async () => {
        try {
            const { data } = await client.query({
                query: GET_TEAM_STATS,
                variables: {
                    teamId: teamId
                }
            });
            const teamStatsData = data.getTeamStats;
            console.log(teamStatsData);
            setTeamStats(teamStatsData);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        getPlayersOnTeam();
        getTeamStats();
    }, []);

    const handlePlayerClick = (playerId) => {
        return () => {
            navigate(`/players/${playerId}`)
        }
    }

    const handlePlayersClick = () => {
        setTeamOptionToggle("players");
    }

    const handleStatsClick = () => {
        console.log("Team Stats: ", teamStats);
        setTeamOptionToggle("stats");
    }

    return (
        <>
            {players.length > 0 && teamStats.length > 0 ? (
                <>
                    <h1>Team Page</h1>
                    <div className={styles.toggle}>
                        <button onClick={handlePlayersClick}>Players</button>
                        <button onClick={handleStatsClick}>Stats</button>
                    </div>
                    {teamOptionToggle === "players" && (
                        <>
                            <h2>Players On Team:</h2>
                            {players.map((player) => (
                                <div key={player.id}>
                                    <PlayerCard player={player} onClick={handlePlayerClick(player.id)}/>
                                </div>
                            ))}
                        </>
                    )}
                    {teamOptionToggle === "stats" && (
                        <>
                            <h2>Team Stats</h2>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Season</th>
                                        <th>PPG</th>
                                        <th>RPG</th>
                                        <th>APG</th>
                                        <th>Rank</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {teamStats.map((season, index) => (
                                        <tr key={index}>
                                            <td>{season.season}</td>
                                            <td>{season.ppg.toFixed(2)}</td>
                                            <td>{season.rpg.toFixed(2)}</td>
                                            <td>{season.apg.toFixed(2)}</td>
                                            <td>{season.confRank}</td>
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
        </>
    )
    
}

export default TeamPage;