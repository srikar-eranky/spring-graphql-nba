import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Homepage from './pages/Home/Homepage';
import Login from './components/login/Login';
import { useAuth } from './contexts/authContext';
import LogOut from './components/logout/Logout';
import Loading from './components/loading/Loading';
import PlayerViewComponent from './components/playerView/playerViewComponent';
import TeamViewComponent from './components/teamView/teamViewComponent';

function App() {

  const { currentUser, signedIn, loading } = useAuth();

  if(loading) {
    return <Loading />
  }

  console.log("Current user: ", currentUser);

  return (
      <Router>
        <Routes>
        <Route path='/' element={
          <>
            <Login />
            <Homepage currentUser={currentUser} />
            <TeamViewComponent />
            <PlayerViewComponent />
            {signedIn && <LogOut /> }
          </>
        } />
        </Routes>
      </Router>
  );
}

export default App;
