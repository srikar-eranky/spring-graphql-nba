import { useApolloClient, gql } from "@apollo/client";
import React, { useState } from "react";
import styles from './createTeam.module.css';

const CreateTeam = () => {
    const CREATE_TEAM = gql`
        mutation CreateTeam($teamInput: TeamInput!) {
            createTeam(teamInput: $teamInput)
        }
    `
    const client = useApolloClient();
    const [name, setName] = useState(null);
    const [city, setCity] = useState(null);
    const [coach, setCoach] = useState(null);
    const [arena, setArena] = useState(null);
    const [founded, setFounded] = useState(null);
    const [conference, setConference] = useState(null);
    const [owner, setOwner] = useState(null);
    const [error, setError] = useState(null);

    const handleNameChange = (event) => {
        setName(event.target.value.trim() === '' ? null : event.target.value);
    };

    const handleCityChange = (event) => {
        setCity(event.target.value.trim() === '' ? null : event.target.value);
    };

    const handleCoachChange = (event) => {
        setCoach(event.target.value.trim() === '' ? null : event.target.value);
    };

    const handleArenaChange = (event) => {
        setArena(event.target.value.trim() === '' ? null : event.target.value);
    };

    const handleFoundedChange = (event) => {
        setFounded(event.target.value.trim() === '' ? null : event.target.value);
    };

    const handleConferenceChange = (event) => {
        setConference(event.target.value.trim() === '' ? null : event.target.value);
    };

    const handleOwnerChange = (event) => {
        setOwner(event.target.value.trim() === '' ? null : event.target.value);
    };

    const createTeam = async () => {
        let teamInput;
        if(!name.includes(city)) {
            const words = name.split(" ");
            const newName = city + " " + words[words.length - 1];
            console.log(newName);
            setName(newName);
        
            teamInput = {
                name: newName,
                city: city,
                coach: coach,
                arena: arena,
                founded: founded,
                conference: conference,
                owner: owner
            }
        } else {
            teamInput = {
                name: name,
                city: city,
                coach: coach,
                arena: arena,
                founded: founded,
                conference: conference,
                owner: owner
            }
        }
        if(name === null) {
            alert("Enter a name");
            return;
        }
        if(conference === null) {
            alert("Select a conference");
            return;
        } else if (conference !== "West" &&
        conference !== "East") {
            alert("Conference must be either East or West.");
            return;
        }
        if(city === null) {
            alert("Enter a city");
            return;
        }
        try {
            const { data, error } = await client.mutate({
                mutation: CREATE_TEAM,
                variables: {
                    teamInput: teamInput
                }
            });
            setError(error.message);
            console.log(data);
        } catch (e) {
            console.error(e);
        }  
    }

    if(error) return <p>Error: {error}</p>

    return (
        <div className={styles.container}>
            <form onSubmit={createTeam} className={styles.form}>
                <h1 style={{ textAlign: "center" }}>Create Team:</h1>
                <div className={styles.row}>
                    <label>Name:</label>
                    <input type="text" value={name || ''} onChange={handleNameChange} />
                    <label>City:</label>
                    <input type="text" value={city || ''} onChange={handleCityChange} />
                </div>
                <div className={styles.row}>
                    <label>Coach:</label>
                    <input type="text" value={coach || ''} onChange={handleCoachChange} />
                    <label>Arena:</label>
                    <input type="text" value={arena || ''} onChange={handleArenaChange} />
                </div>
                <div className={styles.row}>
                    <label>Founded:</label>
                    <input type="text" value={founded || ''} onChange={handleFoundedChange} />
                    <label>Conference:</label>
                    <input type="text" value={conference || ''} onChange={handleConferenceChange} />
                </div>
                <div className={`${styles.row} ${styles.single}`}>
                    <label>Owner:</label>
                    <input type="text" value={owner || ''} onChange={handleOwnerChange} />
                </div>
                <div className={styles.center}>
                    <button type="submit">Submit</button>
                </div>
            </form>
        </div>

    )
}

export default CreateTeam;