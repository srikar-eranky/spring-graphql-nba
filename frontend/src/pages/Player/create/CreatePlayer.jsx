import React, { useEffect, useState } from "react";
import { useApolloClient, gql } from "@apollo/client";
import Loading from "../../../components/loading/Loading";
import SelectPosition from "../../../components/selectPosition/PositionSelectComponent";
import styles from './createPlayer.module.css';

const CreatePlayer = () => {
    const GET_TEAMS = gql`
    {
        teams {
            id
            name
            city
            conference
        }
    }
    `
    
    const CREATE_PLAYER = gql`
        mutation CreatePlayer($playerInput: PlayerInput!) {
            createPlayer(playerInput: $playerInput)
        }
    `
    const client = useApolloClient();
    const [graphError, setError] = useState(null);
    const [isLoading, setLoading] = useState(false);
    const [teams, setTeams] = useState([]);
    const [teamId, setTeamId] = useState(null);
    const [name, setName] = useState(null);
    const [age, setAge] = useState(null);
    const [height, setHeight] = useState(null);
    const [position, setPosition] = useState(null);

    const handleTeamIdChange = (event) => {
        const value = event.target.value;
        setTeamId(value);
    }

    const handleNameChange = (event) => {
        const value = event.target.value;
        setName(value.trim() === '' ? null : value);
    };

    // Handler for age changes
    const handleAgeChange = (event) => {
        const value = event.target.value;
        setAge(value.trim() === '' ? null : Number(value));
    };

    // Handler for height changes
    const handleHeightChange = (event) => {
        const value = event.target.value;
        setHeight(value.trim() === '' ? null : value);
    };

    // Handler for position changes
    const handlePositionChange = (position) => {
        setPosition(position);
    };

    const getTeams = async () => {
        try {
            const { data } = await client.query({
                query: GET_TEAMS
            });
            setTeams(data.teams);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    }

    const createPlayer = async () => {
        try {
            const playerInput = {
                teamId: teamId,
                name: name,
                age: age,
                height: height,
                position: position
            }
            if(teamId === null){
                alert("choose a team");
                return;
            }
            if(name === null) {
                alert("Enter a name");
                return;
            }
            if(position == null) {
                alert("Choose position");
                return;
            }
            const { data } = await client.mutate({
                mutation: CREATE_PLAYER,
                variables: {
                    playerInput: playerInput
                }
            });
            console.log(data);
        } catch (error) {
            setError(error.message);
        }
    }

    useEffect(() => {
        getTeams();
    }, [])
    
    if(isLoading) return <Loading />
    if(graphError) return <p>Error: {graphError}</p>;

    return (
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <div className={styles.wrapper}>
                <h1 style={{ textAlign: "center" }}>Create Player:</h1>
                
                <span className={styles.row}>
                    <label>
                        <select onChange={handleTeamIdChange}>
                            <option value={null}>Select a Team</option>
                            {teams.map((team) => (
                                <option key={team.id} value={team.id}>{team.name}</option>
                            ))}
                        </select>
                    </label>
                    
                    <label>
                        <SelectPosition setPosition={handlePositionChange} />
                    </label>
                </span>
                
                <span className={styles.row}>
                    <label>
                        <b>Name:</b>
                        <input type="text" value={name || ''} onChange={handleNameChange} />
                    </label>
                    
                    <label>
                        <b>Age:</b>
                        <input type="number" value={age || ''} onChange={handleAgeChange} />
                    </label>
                    
                    <label>
                        <b>Height:</b>
                        <input type="text" value={height || ''} onChange={handleHeightChange} />
                    </label>
                </span>
                
                <div className={styles.center}>
                    <button onClick={createPlayer}>Submit</button>
                </div>
            </div>
        </div>

    )
}

export default CreatePlayer;