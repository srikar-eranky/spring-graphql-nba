import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import StatsPage from './pages/statsView/StatsPage';
import Homepage from './pages/Home/Homepage';
import Login from './components/login/Login';
import { useAuth } from './contexts/authContext';
import LogOut from './components/logout/Logout';
import Loading from './components/loading/Loading';

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
            {signedIn && <LogOut /> }
          </>
        } />
        {signedIn ? (
          <Route path='/stats' element={<StatsPage />} />
          ) : (
          <Route path="/stats" element={<Navigate to="/" />} />
        )}
        </Routes>
      </Router>
  );
}

export default App;
