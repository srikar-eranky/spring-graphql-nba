import React, { useState } from "react";

const SearchBar = () => {
    const [query, setQuery] = useState("");
    const [teams, setTeams] = useState([]);
    const [filteredTeams, setFilteredTeams] = useState([]);

    return (
        <form>
            <input 
                type="text"
                placeholder="Search for a team..."
                value={query}
                onChange={(e) => setQuery(e.target.value)}/>
        </form>
    )
}