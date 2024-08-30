import { gql, useApolloClient } from "@apollo/client";
import React, { useEffect, useState } from "react";
import styles from './positionSelectComponent.module.css';

const SelectPosition = ({ setPosition, position }) => {
    
    const client = useApolloClient();
    const [positions, setPositions] = useState([]);

    const GET_POSITIONS = gql`
    {
        positions
    }
    `

    const getPositions = async () => {
        try {
            const { data } = await client.query({
                query: GET_POSITIONS
            });
            setPositions(data.positions);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        getPositions();
    }, [])

    const handlePositionChange = (event) => {
        setPosition(event.target.value);
    };

    return (
        <div className={styles.positionSelect}>
            <select name="position" defaultValue={position} onChange={handlePositionChange}>
                <option value="">Select Position</option>
                {positions.map((position, index) => (
                    <option key={index} value={position}>{position}</option>
                ))}
            </select>
        </div>
    )
}

export default SelectPosition;