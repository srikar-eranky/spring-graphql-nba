import React from "react";
import { useQuery, gql } from "@apollo/client";
import Loading from "../loading/Loading";
import TeamCard from "./teamCard/teamCardComponent";
import styles from './teamViewComponent.module.css';
import { useNavigate } from "react-router-dom";

const TeamViewComponent = () => {

    const navigate = useNavigate();

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

    const { loading, error, data } = useQuery(GET_TEAMS);

    if(loading) return <Loading />;
    if (error) return <p>Error: {error.message}</p>;

    const teamsData = data.teams;
    const easternTeams = teamsData.filter((team) => team.conference === "East");
    const westernTeams = teamsData.filter((team) => team.conference === "West");

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
                    <h2>Eastern Conference: </h2>
                    <div>
                        {easternTeams.map((team) => (
                            <TeamCard team={team} key={team.id} onClick={handleTeamClick(team.id)}/>
                        ))}
                    </div>
                </div>
                <div className="conference-container">
                    <h2>Western Conference:</h2>
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