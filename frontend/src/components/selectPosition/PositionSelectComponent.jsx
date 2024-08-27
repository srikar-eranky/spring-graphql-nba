import React from "react";

const SelectPosition = ({ setPosition }) => {

    const handlePositionChange = (event) => {
        setPosition(event.target.value);
    };

    return (
        <>
            <label>Select Position:</label>
            <select name="position" onChange={handlePositionChange}>
                <option value="">--</option>
                <option value="FORWARD">FORWARD</option>
                <option value="GUARD">GUARD</option>
                <option value="CENTER">CENTER</option>
                <option value="FORWARD-CENTER">FORWARD-CENTER</option>
                <option value="CENTER-FORWARD">CENTER-FORWARD</option>
                <option value="GUARD-FORWARD">GUARD-FORWARD</option>
                <option value="FORWARD-GUARD">FORWARD-GUARD</option>
            </select>
        </>
    )
}

export default SelectPosition;