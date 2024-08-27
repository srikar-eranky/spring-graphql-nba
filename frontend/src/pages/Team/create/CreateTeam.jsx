import { useApolloClient, gql } from "@apollo/client";
import React, { useState } from "react";

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
        const teamInput = {
            name: name,
            city: city,
            coach: coach,
            arena: arena,
            founded: founded,
            conference: conference,
            owner: owner
        }
        if(name === null) {
            alert("Enter a name");
            return;
        }
        try {
            const { data } = await client.mutate({
                mutation: CREATE_TEAM,
                variables: {
                    teamInput: teamInput
                }
            });
            console.log(data);
        } catch (error) {
            console.error(error);
        }  
    }

    return (
        <form onSubmit={createTeam}>
            <div>
                <label>Name:</label>
                <input type="text" value={name || ''} onChange={handleNameChange} />
            </div>
            <div>
                <label>City:</label>
                <input type="text" value={city || ''} onChange={handleCityChange} />
            </div>
            <div>
                <label>Coach:</label>
                <input type="text" value={coach || ''} onChange={handleCoachChange} />
            </div>
            <div>
                <label>Arena:</label>
                <input type="text" value={arena || ''} onChange={handleArenaChange} />
            </div>
            <div>
                <label>Founded:</label>
                <input type="text" value={founded || ''} onChange={handleFoundedChange} />
            </div>
            <div>
                <label>Conference:</label>
                <input type="text" value={conference || ''} onChange={handleConferenceChange} />
            </div>
            <div>
                <label>Owner:</label>
                <input type="text" value={owner || ''} onChange={handleOwnerChange} />
            </div>
            <button type="submit">Submit</button>
        </form>
    )
}

export default CreateTeam;