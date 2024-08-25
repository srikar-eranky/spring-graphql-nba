import React from "react";
import { useQuery, gql } from "@apollo/client";
import Loading from "../loading/Loading";
import TeamCard from "./teamCard/teamCardComponent";

const TeamViewComponent = () => {
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

    return (
        <>
            <h1>Teams List:</h1>
            {teamsData.map((team) => (
                <div key={team.id}>
                    <TeamCard team={team} />
                </div>
                
            ))}
        </>
    )

}

export default TeamViewComponent;