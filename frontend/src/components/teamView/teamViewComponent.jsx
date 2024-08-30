import React, { useEffect, useState } from "react";
import { useQuery, gql, useApolloClient } from "@apollo/client";
import Loading from "../loading/Loading";
import TeamCard from "./teamCard/teamCardComponent";
import styles from './teamViewComponent.module.css';
import { useLocation, useNavigate } from "react-router-dom";

const TeamViewComponent = () => {

    const navigate = useNavigate();
    const location = useLocation();
    const client = useApolloClient();
    const [teams, setTeams] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

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

    const getTeams = async () => {
        setLoading(true);
        try {
            const { data } = await client.query({
                query: GET_TEAMS,
                fetchPolicy: 'network-only'
            });
            setTeams(data.teams);
            setLoading(false);
        } catch (error) {
            setError(error.message);
        }
    }

    useEffect(() => {
        getTeams();
    }, [location]);

    if(loading) return <Loading />;
    if (error) return <p>Error: {error}</p>;

    const easternTeams = teams.filter((team) => team.conference === "East");
    const westernTeams = teams.filter((team) => team.conference === "West");

    const handleTeamClick = (teamId) => {
        return () => {
            navigate(`/teams/${teamId}`);
        }   
    }

    return (
        <div className={styles.teamContainer}>
            <h1>Teams List:</h1>
            <div className={styles.teamWrapper}>
                <div className={styles.conferenceContainer}>
                    <h2 style={{ color: "blue" }}>Eastern Conference: </h2>
                    <div>
                        {easternTeams.map((team) => (
                            <TeamCard team={team} key={team.id} onClick={handleTeamClick(team.id)}/>
                        ))}
                    </div>
                </div>
                <div className={styles.conferenceContainer}>
                    <h2 style={{ color: "red" }}>Western Conference:</h2>
                    <div>
                        {westernTeams.map((team) => (
                            <TeamCard team={team} key={team.id} onClick={handleTeamClick(team.id)}/>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )

}

export default TeamViewComponent;