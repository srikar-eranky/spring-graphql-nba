import React, { useEffect, useState } from "react";
import styles from './teamPage.module.css';
import { useNavigate, useParams } from "react-router-dom";
import { useApolloClient, gql } from "@apollo/client";
import PlayerCard from "../../../components/playerView/playerCard/playerCardComponent";
import Loading from "../../../components/loading/Loading";
import { useAuth } from "../../../contexts/authContext";

const TeamPage = () => {
    const { teamId } = useParams();
    const { signedIn } = useAuth();
    const client = useApolloClient();
    const navigate = useNavigate();
    const [players, setPlayers] = useState([]);
    const [teamStats, setTeamStats] = useState([]);
    const [teamName, setTeamName] = useState(null);
    const [city, setCity] = useState(null);
    const [arena, setArena] = useState(null);
    const [founded, setFounded] = useState(null);
    const [owner, setOwner] = useState(null);
    const [coach, setCoach] = useState(null);
    const [conference, setConference] = useState(null);
    const [teamOptionToggle, setTeamOptionToggle] = useState("details");
    const [updateMode, setUpdateMode] = useState(false);
    const [deleted, setDeleted] = useState(false);
    const [error, setError] = useState(null);

    const GET_TEAM_DETAILS = gql`
        query GetTeam($teamId: ID!) {
            teamById(id: $teamId) {
                id
                name
                city
                arena
                founded
                owner
                coach
                conference
            }
        }
    `

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
                played
                won
                lost
                ppg
                rpg
                apg
                fgpercent
                ftpercent
                fgthreepercent
                season
                confRank
            }
        }`;

    const UPDATE_TEAM = gql`
        mutation UpdateTeam($teamId: ID, $teamInput: TeamInput!) {
            updateTeam(teamId: $teamId, teamInput: $teamInput)
        }
    `

    const DELETE_TEAM = gql`
        mutation DeleteTeam($teamId: ID) {
            deleteTeam(teamId: $teamId) {
                name
            }
        }
    `
    
    const getTeamDetails = async () => {
        try {
            const { data } = await client.query({
                query: GET_TEAM_DETAILS,
                variables: {
                    teamId: teamId
                },
                fetchPolicy: 'network-only'
            })
            const teamData = data.teamById;
            if(teamData) {
                setTeamName(teamData.name);
                setCity(teamData.city);
                setArena(teamData.arena);
                setCoach(teamData.coach);
                setOwner(teamData.owner);
                setFounded(teamData.founded);
                setConference(teamData.conference);
            }
        } catch (error) {
            setError(error.message);
        }
    }

    const getPlayersOnTeam = async () => {
        try {
            const { data } = await client.query({
                query: GET_PLAYERS_ON_TEAM,
                variables: {
                    teamId: teamId
                },
                fetchPolicy: 'network-only'
            })
            const playersOnTeam = data.playersByTeamId;
            setPlayers(playersOnTeam);
        } catch (error) {
            setError(error.message);
        }
    }

    const getTeamStats = async () => {
        try {
            const { data } = await client.query({
                query: GET_TEAM_STATS,
                variables: {
                    teamId: teamId
                },
                fetchPolicy: 'network-only'
            });
            const teamStatsData = data.getTeamStats;
            console.log(teamStatsData);
            setTeamStats(teamStatsData);
        } catch (error) {
            setError(error.message);
        }
    }

    useEffect(() => {
        getTeamDetails();
        getPlayersOnTeam();
        getTeamStats();
    }, []);

    const handlePlayerClick = (playerId) => {
        return () => {
            navigate(`/players/${playerId}`)
        }
    }

    const handlePlayersClick = () => {
        setTeamOptionToggle("details");
    }

    const handleStatsClick = () => {
        setTeamOptionToggle("stats");
    }

    const changeUpdateMode = () => {
        setUpdateMode(!updateMode);
    }

    const handleTeamNameChange = (event) => {
        setTeamName(event.target.value);
    }
    
    const handleCityChange = (event) => {
        setCity(event.target.value);
    }
    
    const handleArenaChange = (event) => {
        setArena(event.target.value);
    }
    
    const handleFoundedChange = (event) => {
        setFounded(event.target.value);
    }
    
    const handleOwnerChange = (event) => {
        setOwner(event.target.value);
    }
    
    const handleCoachChange = (event) => {
        setCoach(event.target.value);
    }
    
    const handleConferenceChange = (event) => {
        setConference(event.target.value);
    }

    const updateTeam = async () => {
        if(!teamName.includes(city)) {
            const words = teamName.split(" ");
            const newName = city + " " + words[words.length - 1]
            setTeamName(newName);
        }
        try {
            const teamInput = {
                name: teamName,
                city: city,
                arena: arena,
                founded: founded,
                owner: owner,
                coach: coach,
                conference: conference
            }
            const { data } = client.mutate({
                mutation: UPDATE_TEAM,
                variables: {
                    teamId: teamId,
                    teamInput: teamInput
                }
            })
            changeUpdateMode(); 
        } catch (error) {
            setError(error.message);
        }
    }

    const deleteTeam = async () => {
        try {
            const { data } = client.mutate({
                mutation: DELETE_TEAM,
                variables: {
                    teamId: teamId
                }
            });
            setDeleted(true);
            setTimeout(() => {
                navigate('/');
            }, 1500);
            console.log("Deleted Team");
        } catch (error) {
            setError(error.message);
        }
    }
    
    if(error) return <p>Error: {error}</p>

    return (
        <>
          {players.length > 0 && teamStats.length > 0 && !deleted ? (
            <div>
              <div className={styles.teamHeader}>
                <h1>{teamName}</h1>
                <div className={styles.toggle}>
                  <button
                    onClick={handlePlayersClick}
                    style={{
                      borderRadius: '10px',
                      backgroundColor: teamOptionToggle === 'details' ? 'lightcyan' : '',
                      padding: '5px'
                    }}
                  >
                    Details
                  </button>
                  <button
                    onClick={handleStatsClick}
                    style={{
                      borderRadius: '10px',
                      backgroundColor: teamOptionToggle === 'stats' ? 'lightcyan' : '',
                      padding: '5px'
                    }}
                  >
                    Stats
                  </button>
                </div>
              </div>
              {teamOptionToggle === 'details' && (
                <div>
                  <div style={{ margin: "10px" }}>
                    {!updateMode ? (
                      <div className={styles.teamView}>
                        <h2>Team Details:</h2>
                        <div style={{ display: 'flex', gap: '10px' }}>
                          <p><b>City:</b> {city}</p>
                          <p><b>Arena:</b> {arena}</p>
                        </div>
                        <div style={{ display: 'flex', gap: '10px' }}>
                          <p><b>Founded:</b> {founded}</p>
                          <p><b>Owner:</b> {owner}</p>
                        </div>
                        <div style={{ display: 'flex', gap: '10px' }}>
                          <p><b>Coach:</b> {coach}</p>
                          <p><b>Conference: </b> {conference}</p>
                        </div>
                      </div>
                    ) : (
                      <div className={styles.formWrapper}>
                        <h1 style={{ textAlign: "center" }}>Update Team:</h1>
                        <div className={styles.formGroup}>
                          <label>
                            Update Name:
                            <input
                              type="text"
                              placeholder={teamName}
                              value={teamName}
                              onChange={handleTeamNameChange}
                            />
                          </label>
                          <label>
                            Update City:
                            <input
                              type="text"
                              placeholder={city}
                              value={city}
                              onChange={handleCityChange}
                            />
                          </label>
                        </div>
                        <div className={styles.formGroup}>
                          <label>
                            Update Arena:
                            <input
                              type="text"
                              placeholder={arena}
                              value={arena}
                              onChange={handleArenaChange}
                            />
                          </label>
                          <label>
                            Update Founded Year:
                            <input
                              type="number"
                              placeholder={founded}
                              value={founded}
                              onChange={handleFoundedChange}
                            />
                          </label>
                        </div>
                        <div className={styles.formGroup}>
                          <label>
                            Update Owner:
                            <input
                              type="text"
                              placeholder={owner}
                              value={owner}
                              onChange={handleOwnerChange}
                            />
                          </label>
                          <label>
                            Update Coach:
                            <input
                              type="text"
                              placeholder={coach}
                              value={coach}
                              onChange={handleCoachChange}
                            />
                          </label>
                        </div>
                        <div className={styles.formGroup}>
                          <label>
                            Update Conference:
                            <select value={conference} onChange={handleConferenceChange}>
                              <option value="West">West</option>
                              <option value="East">East</option>
                            </select>
                          </label>
                          <button onClick={updateTeam} className={styles.saveButton}>Save</button>
                        </div>
                      </div>
                    )}
                    {signedIn && !updateMode && (
                      <div className={styles.centerButtons}>
                        <button onClick={changeUpdateMode}>Update Team</button>
                        <button onClick={deleteTeam}>Delete Team</button>
                      </div>
                    )}
                  </div>
                  <div className={styles.playerCards}>
                    {players.map((player, index) => (
                        <PlayerCard key={index} player={player} onClick={handlePlayerClick(player.id)}/>
                    ))}
                  </div>
                </div>
              )}
              {teamOptionToggle === 'stats' && (
                <div className={styles.teamView}>
                  <h2>Team Stats</h2>
                  <table>
                    <thead>
                      <tr>
                        <th>Season</th>
                        <th>GP</th>
                        <th>W</th>
                        <th>L</th>
                        <th>Win %</th>
                        <th>PPG</th>
                        <th>RPG</th>
                        <th>APG</th>
                        <th>FG %</th>
                        <th>FT %</th>
                        <th>FG3 %</th>
                        <th>Rank</th>
                      </tr>
                    </thead>
                    <tbody>
                      {teamStats.slice().reverse().map((season, index) => (
                        <tr key={index}>
                          <td>{season.season}</td>
                          <td>{season.played}</td>
                          <td>{season.won}</td>
                          <td>{season.lost}</td>
                          <td>{((season.won / season.played) * 100).toFixed(1)}</td>
                          <td>{season.ppg.toFixed(2)}</td>
                          <td>{season.rpg.toFixed(2)}</td>
                          <td>{season.apg.toFixed(2)}</td>
                          <td>{(season.fgpercent * 100).toFixed(1)}</td>
                          <td>{(season.ftpercent * 100).toFixed(1)}</td>
                          <td>{(season.fgthreepercent * 100).toFixed(1)}</td>
                          <td>{season.confRank}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                  
                </div>
              )}
              
            </div>
          ) : deleted ? (
            <h1>Successfully deleted team!</h1>
          ) : (
            <Loading />
          )}
        </>
      );
    
}

export default TeamPage;