import React, { useEffect, useState } from "react";

const Homepage = ({ currentUser }) => {

    console.log(currentUser);

    return (
        <>
            <h1>Home</h1>
            {currentUser === null || currentUser === undefined ? (
                <p>No user logged in</p>
            ) : (
                <p>Welcome, {currentUser.displayName}</p>
            )}
        </>
    )
}

export default Homepage;