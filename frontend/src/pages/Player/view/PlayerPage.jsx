import React, { useEffect, useState } from "react";
import styles from './playerPage.module.css';
import { useNavigate, useParams } from "react-router-dom";
import { useApolloClient, gql } from "@apollo/client";
import Loading from "../../../components/loading/Loading";
import SelectPosition from "../../../components/selectPosition/PositionSelectComponent";
import { useAuth } from "../../../contexts/authContext";

const PlayerPage = () => {
    const { playerId } = useParams();
    const navigate = useNavigate();
    const client = useApolloClient();
    const { signedIn } = useAuth();
    const [player, setPlayer] = useState(null);
    const [stats, setStats] = useState(null);
    const [teamId, setTeamId] = useState(null);
    const [teamName, setTeamName] = useState(null);
    const [name, setName] = useState(null);
    const [age, setAge] = useState(null);
    const [height, setHeight] = useState(null);
    const [position, setPosition] = useState(null);
    const [optionToggle, setOptionToggle] = useState("profile");
    const [error, setError] = useState(null);
    const [updateMode, setUpdateMode] = useState(false);
    const [deletedPlayerName, setDeletedPlayerName] = useState(null);
    const [deleted, setDeleted] = useState(false);
    const [teams, setTeams] = useState([]);

    const GET_PLAYER = gql`
        query GetPlayer($playerId: ID){
            playerByPlayerId(id: $playerId) {
                id
                teamId
                name
                age
                height
                position
            }
        }`;
    
    const GET_TEAM = gql`
        query GetTeam($teamId: ID) {
            teamById(id: $teamId) {
                id
                name
            }
        }
    `

    const GET_TEAMS = gql`
    {
        teams {
            id
            name
        }
    }`;

    const GET_PLAYER_STATS = gql`
        query GetPlayerStats($playerId: ID) {
            getPlayerStats(playerId: $playerId) {
                minpergame
                ppg
                apg
                rpg
                stealspergame
                bpg
                tov
                fgpercent
                ftpercent
                fgthreepercent
                season
            }
        }`;
    
    const DELETE_PLAYER = gql`
        mutation DeletePlayer($playerId: ID) {
            deletePlayer(playerId: $playerId) {
                name
            }
        }
    `

    const UPDATE_PLAYER = gql`
        mutation UpdatePlayer($playerId: ID, $playerInput: PlayerInput!) {
            updatePlayer(playerId: $playerId, playerInput: $playerInput)
        }
    `

    const getPlayerDetails = async () => {
        try {
            const { data } = await client.query({
                query: GET_PLAYER,
                variables: {
                    playerId: playerId
                },
                fetchPolicy: 'network-only'
            });
            const playerData = data.playerByPlayerId;
            console.log(playerData);
            setPlayer(playerData);
            setName(playerData.name);
            setTeamId(playerData.teamId);
            setAge(playerData.age);
            setHeight(playerData.height);
            setPosition(playerData.position);
        } catch (error) {
            setError(error.message);
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
            setError(error.message);
        }
    }

    const getCurrentTeam = async () => {
        if(!player || !player.teamId) {
            console.log("Player not found");
        }
        try {
            const { data } = await client.query({
                query: GET_TEAM,
                variables: {
                    teamId: player.teamId
                }
            });
            console.log(data.teamById);
            setTeamId(data.teamById.id);
            setTeamName(data.teamById.name); 
        } catch (error) {
            setError(error.message);
        }
    }

    const getTeams = async () => {
        try {
            const { data } = await client.query({
                query: GET_TEAMS
            });
            setTeams(data.teams);
        } catch (error) {
            setError(error.message);
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            await getPlayerDetails();
            await getPlayerStats();
            await getTeams();
        }
        fetchData();
    }, []);

    useEffect(() => {
        if(player) {
            getCurrentTeam();
        }
    }, [player]);

    const handleProfileClick = () => {
        setOptionToggle("profile");
    }

    const handleStatsClick = () => {
        setOptionToggle("stats");
    }

    const handleUpdateMode = () => {
        setUpdateMode(true);
    }

    const handleTeamChange = (event) => {
        setTeamId(event.target.value);
    }
    
    const updateName = (event) => {
        setName(event.target.value);
    }

    const updateAge = (event) => {
        setAge(event.target.value);
    }

    const updateHeight = (event) => {
        setHeight(event.target.value);
    }

    const updatePosition = (position) => {
        setPosition(position);
    }

    const handleDeletePlayer = async () => {
        try {
            const { data } = await client.mutate({
                mutation: DELETE_PLAYER,
                variables: {
                    playerId: playerId
                }
            });
            setDeletedPlayerName(data.deletePlayer.name);
            setDeleted(true);
            setTimeout(() => {
                console.log("Delayed for 3 seconds");
            }, 3000);
            navigate('/');
        } catch (error) {
            setError(error.message);
        }
    }

    const handlePlayerUpdate = async () => {
        try {
            const playerInput = {
                teamId: teamId,
                name: name,
                age: age,
                height: height,
                position: position
            }
            const { data } = client.mutate({
                mutation: UPDATE_PLAYER,
                variables: {
                    playerId: playerId,
                    playerInput: playerInput
                }
            });
            console.log("player updated?", data);
            setUpdateMode(false);
            window.location.reload();
        } catch (error) {
            setError(error.message);
        }
    }

    if(error) return <p>Error: {error}</p>;

    return (
        <div>
            {player && teamId && teamName && height && position && !deleted ? (
                <div className={styles.playerWrapper}>
                    <h1>{player.name}</h1>
                    <div className={styles.toggle}>
                        <button style={{ backgroundColor: optionToggle === "profile" ? "lightcyan" : "" }} className={styles.profileStats} onClick={handleProfileClick}>Profile</button>
                        <button style={{ backgroundColor: optionToggle === "stats" ? "lightcyan" : "" }} className={styles.profileStats} onClick={handleStatsClick}>Stats</button>
                    </div>
                    {optionToggle === "profile" && (
                        <div>
                            {updateMode ? (
                                <div className={styles.updateForm}>
                                    <div className={styles.updateFormRow}>
                                        <select onChange={handleTeamChange} defaultValue={teamId}>
                                            {teams.map((team) => (
                                                <option key={team.id} value={team.id}>{team.name}</option>
                                            ))}
                                        </select>
                                        <label>
                                            <SelectPosition setPosition={updatePosition} position={position}/>
                                        </label>
                                    </div>
                                    <div className={styles.updateFormRow}>
                                        <label>
                                            Change Name:
                                            <input 
                                                type="text" 
                                                placeholder={name} 
                                                value={name} 
                                                onChange={updateName} />
                                        </label>
                                        <label>
                                            Change Age:
                                            <input 
                                                type="number"
                                                placeholder={age}
                                                value={age}
                                                onChange={updateAge} />
                                        </label>
                                        <label>
                                            Change Height:
                                            <input 
                                                type="text"
                                                placeholder={height}
                                                value={height}
                                                onChange={updateHeight} />
                                        </label>
                                    </div>
                                    <div className={styles.updateButton}>
                                        <button onClick={handlePlayerUpdate}>Save Player</button>
                                        <button onClick={() => setUpdateMode(false)}>Cancel</button>
                                    </div>
                                </div>
                            ) : (
                                <div style={{ textAlign: "center" }}>
                                    <p><b>Age:</b> {player.age}</p>
                                    <p><b>Team:</b> {teamName}</p>
                                    <p><b>Height:</b> {player.height}</p>
                                    <p><b>Position:</b> {player.position}</p>
                                </div>
                            )}
                        </div>
                    )}
                    {optionToggle === "stats" && (
                        <>
                            <h1>Player Stats:</h1>
                            <table>
                                <tbody>
                                    <tr>
                                        <th>Season</th>
                                        <th>MPG</th>
                                        <th>PPG</th>
                                        <th>APG</th>
                                        <th>RPG</th>
                                        <th>STL</th>
                                        <th>BLK</th>
                                        <th>TOV</th>
                                        <th>FG %</th>
                                        <th>FT %</th>
                                        <th>FG3 %</th>
                                    </tr>
                                    {stats.map((season, index) => (
                                        <tr key={index}>
                                            <td>{season.season}</td>
                                            <td>{season.minpergame.toFixed(1)}</td>
                                            <td>{season.ppg.toFixed(2)}</td>
                                            <td>{season.apg.toFixed(2)}</td>
                                            <td>{season.rpg.toFixed(2)}</td>
                                            <td>{season.stealspergame.toFixed(2)}</td>
                                            <td>{season.bpg.toFixed(2)}</td>
                                            <td>{season.tov.toFixed(2)}</td>
                                            <td>{((season.fgpercent) * 100).toFixed(1)}</td>
                                            <td>{((season.ftpercent) * 100).toFixed(1)}</td>
                                            <td>{((season.fgthreepercent) * 100).toFixed(1)}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </>    
                    )}
                    {signedIn && optionToggle !== "stats" && !updateMode &&
                    <div className={styles.updateAndDelete}>
                        <button onClick={handleUpdateMode}>Update Player</button>
                        <button onClick={handleDeletePlayer}>Delete Player</button>
                    </div>
                    }
                </div>
            ) : deleted ? (
                <>
                    <p>{deletedPlayerName} successfully deleted</p>
                </>
            ) : (
                <Loading />
            )}
        </div>
    )
}

export default PlayerPage;