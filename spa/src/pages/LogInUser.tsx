import React, { useState } from 'react';
import axios from 'axios';
import './styles.css';
import {useUserSession } from '../model/UserContext';
import { useNavigate } from 'react-router-dom';
import {Pathnames} from "../router/pathnames.ts";
/**
 * To na tę chwilę zastępuje nam logowanie się Użytkownika. Po prostu podajemy username i szukamy kogoś o takim username.
 */
interface EntityId {
    uuid: string;
}

interface ClientType {
    _clazz: string;
    entityId: EntityId;
    maxRentedMachines: number;
    name: string;
}

enum Role {
    admin = "ADMIN",
    resourcemanager = "RESOURCE_MANAGER",
    client = "CLIENT",
}

interface User {
    entityId: EntityId;
    firstName: string;
    surname: string;
    username: string;
    emailAddress: string;
    role: Role;
    active: boolean;
    clientType: ClientType | null;
    currentRents: number;
}

export const LogInUser = () => {
    const [username, setUsername] = useState<string>('');
    const [userData, setUserData] = useState<User | null>(null);
    const [error, setError] = useState<string | null>(null);
    const {setCurrentUser} = useUserSession();
    const navigate = useNavigate();

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
            const response = await axios.get<User>(
                `/api/client/findClient/${username}`,
                {
                    headers: {
                        'ngrok-skip-browser-warning': '69420'
                    }
                }
            );
            setUserData(response.data);
            setCurrentUser(response.data);
            setError(null);
            if(response.data != null) {
                if (response.data.role === "CLIENT") {
                    navigate(Pathnames.user.homePage);
                } else if (response.data.role === "ADMIN") {
                    navigate(Pathnames.admin.homePage);
                } else if (response.data.role === "RESOURCE_MANAGER") {
                    navigate(Pathnames.moderator.homePage);
                }
            }
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
                {/*<label htmlFor="username">Username</label>*/}
                <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder={"Username"}
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
                    <p><strong>Client Type:</strong> {userData.clientType?._clazz}</p>
                    <p><strong>Active:</strong> {userData.active ? 'Yes' : 'No'}</p>
                    <p><strong>Current Rents:</strong> {userData.currentRents}</p>
                </div>
            )}
        </div>
    );
};

// export default LogInUser;
