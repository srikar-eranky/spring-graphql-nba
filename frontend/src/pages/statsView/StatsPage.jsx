import React, { useEffect, useState } from "react";
import { useQuery, gql } from "@apollo/client";

const StatsPage = () => {
    
    const GET_PLAYER_STATS = gql`
    {
        getPlayerStats(playerId: 1) {
            minpergame
            ppg
            apg
            rpg
            stealspergame
            fgpercent
            ftpercent
            season
        }
    }
    `

    const { loading, error, data } = useQuery(GET_PLAYER_STATS);

    if(loading) return (<>Loading...</>)
    if (error) return <p>Error: {error.message}</p>;

    const stats = data.getPlayerStats[0];

    console.log("Stats: ", stats);
    console.log("Data: ", data);
    
    return (
        <>
            <h1>View Stats</h1>
            <div>
                <p>Minutes per Game: {stats.minpergame}</p>
                <p>Points per Game: {stats.ppg}</p>
                <p>Assists per Game: {stats.apg}</p>
                <p>Rebounds per Game: {stats.rpg}</p>
                <p>Steals per Game: {stats.stealspergame}</p>
                <p>Field Goal Percentage: {stats.fgpercent}%</p>
                <p>Free Throw Percentage: {stats.ftpercent}%</p>
                <p>Season: {stats.season}</p>
            </div>
        </>
    )
}

export default StatsPage;