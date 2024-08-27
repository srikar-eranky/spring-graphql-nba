import React from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Homepage from './pages/Home/Homepage';
import Login from './components/login/Login';
import { useAuth } from './contexts/authContext';
import LogOut from './components/logout/Logout';
import Loading from './components/loading/Loading';
import TeamViewComponent from './components/teamView/teamViewComponent';
import TeamPage from './pages/Team/view/TeamPage';
import PlayerPage from './pages/Player/view/PlayerPage';
import CreatePlayer from './pages/Player/create/CreatePlayer';
import CreateTeam from './pages/Team/create/CreateTeam';
import Navbar from './components/navbar/Navbar';

function App() {

  const { currentUser, signedIn, loading } = useAuth();

  if(loading) {
    return <Loading />
  }

  console.log("Current user: ", currentUser);

  return (
      <Router>
        <Navbar />
        <Routes>
          <Route path='/' element={
            <>
              <Homepage currentUser={currentUser} />
              <TeamViewComponent />
            </>
          } />
          <Route path='/teams/:teamId' element={<TeamPage />} />
          <Route path='/players/:playerId' element={<PlayerPage />} />
          {signedIn && (
            <>
              <Route path='/players/createPlayer' element={<CreatePlayer />} />
              <Route path='/teams/createTeam' element={<CreateTeam />} />
            </>
          )}
        </Routes>
      </Router>
  );
}

export default App;
