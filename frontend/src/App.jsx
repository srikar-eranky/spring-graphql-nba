import './App.css';
import React, { useState } from 'react';
import { useQuery, gql } from "@apollo/client";

function App() {

  const [isAuthenticated, setAuthenticated] = useState(false)

  const handleLogin = () => {
    setAuthenticated(true);
  }

  const GET_TEAM = gql `
  {
    teamById(id: 1) { 
      name
      coach
      city
      players {
        name
      }
    }
  }
`

  const GET_TEAM_STATS = gql `
  {
    getTeamStats(teamId: 1) {
      played
      won
      lost
      ppg
      rpg
      apg
      fgpercent
      ftpercent
    }
  }
  `

  const teamInfo = useQuery(GET_TEAM);
  const teamStats = useQuery(GET_TEAM_STATS);

  if (teamInfo.loading) return "...Loading";
  if (teamStats.error) return (
                <div className='App'>
                  <header className='App-header'>
                    <p>{teamStats.error.message}</p>
                  </header>
                </div>
                )

  const { teamById } = teamInfo.data;
  const { getTeamStats } = teamStats.data;

  console.log("Team:", teamById)

  return (
    <div className="App">
      <header className="App-header">
        <h1>Players on the {teamById.name}</h1>
        {teamById.players.map((player) => (
          <div key={player.name}>
            {player.name}
          </div>
        ))}
        <h2>Team Info:</h2>
        <ul>
          <li>Coach: {teamById.coach}</li>
          <li>City: {teamById.city}</li>
        </ul>
        <h2>Team Stats:</h2>
        <ul>
          <li>Team PPG: {getTeamStats.ppg}</li>
          <li>Team APG: {getTeamStats.apg}</li>
          <li>Team RPG: {getTeamStats.rpg}</li>
          <li>Win Percentage: {getTeamStats.won / getTeamStats.played}</li>
          <li>FG %: {getTeamStats.fgpercent}</li>
          <li>FT %: {getTeamStats.ftpercent}</li>
        </ul>
      </header>
    </div>
  );
}

export default App;
