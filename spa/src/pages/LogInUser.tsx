import React, { useState } from 'react';
import axios from 'axios';
import './styles.css';
/**
 * To na tę chwilę zastępuje nam logowanie się Użytkownika. Po prostu podajemy username i szukamy kogoś o takim username.
 */
interface User {
    entityId: { uuid: string };
    firstName: string;
    surname: string;
    username: string;
    emailAddress: string;
    role: string;
    active: boolean;
    clientType: { _clazz: string };
    currentRents: number;
}

export const LogInUser = () => {
    const [username, setUsername] = useState<string>('');
    const [userData, setUserData] = useState<User | null>(null);
    const [error, setError] = useState<string | null>(null);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    };

    const handleLogIn = async () => {
        if (!username.trim()) {
            setError('Username is required');
            setUserData(null);
            return;
        }

        try {
            const response = await axios.get<User>(`/api/client/findClient/${username}`);
            setUserData(response.data);
            setError(null);
        } catch (err) {
            console.error(err);
            setError('User not found or an error occurred.');
            setUserData(null);
        }
    };

    return (
        <div className="container">
            <h2>Log In</h2>
            <div>
                <label htmlFor="username">Username</label>
                <input
                    type="text"
                    id="username"
                    name="username"
                    value={username}
                    onChange={handleChange}
                />
            </div>
            <button onClick={handleLogIn}>Log In</button>
            {error && <p className="error">{error}</p>}
            {userData && (
                <div className="user-details">
                    <h3>User Details:</h3>
                    <p><strong>First Name:</strong> {userData.firstName}</p>
                    <p><strong>Surname:</strong> {userData.surname}</p>
                    <p><strong>Email:</strong> {userData.emailAddress}</p>
                    <p><strong>Role:</strong> {userData.role}</p>
                    <p><strong>Client Type:</strong> {userData.clientType._clazz}</p>
                    <p><strong>Active:</strong> {userData.active ? 'Yes' : 'No'}</p>
                    <p><strong>Current Rents:</strong> {userData.currentRents}</p>
                </div>
            )}
        </div>
    );
};

// export default LogInUser;
