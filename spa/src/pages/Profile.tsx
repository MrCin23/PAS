import React, { useEffect, useState } from 'react';
import './styles.css';
import { useUserSession } from '../model/UserContext';
import { useNavigate } from 'react-router-dom';
import {Pathnames} from "../router/pathnames.ts";

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
    moderator = "MODERATOR",
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
    clientType: ClientType;
    currentRents: number;
}

export const UserProfile = () => {
    const [userData, setUserData] = useState<User | null>(null);
    const { currentUser } = useUserSession();
    const navigate = useNavigate();

    const handleNavigate = () => {
        if (!currentUser) {
            console.error("Użytkownik nie jest zalogowany.");
            return;
        }

        let path;
        switch (currentUser.role) {
            case "ADMIN":
                path = Pathnames.admin.editProfile;
                break;
            case "RESOURCE_MANAGER":
                path = Pathnames.moderator.editProfile;
                break;
            case "CLIENT":
                path = Pathnames.user.editProfile;
                break;
            default:
                console.error("Nieznana rola użytkownika:", currentUser.role);
                return;
        }

        navigate(path);
    };

    useEffect(() => {
        // Ustaw dane użytkownika w stanie tylko wtedy, gdy użytkownik jest zalogowany i aktywny.
        if (currentUser && currentUser.active) {
            setUserData(currentUser);
        }
    }, [currentUser]);

    if (currentUser == null) {
        return <div>User not logged</div>;
    }

    if (!currentUser.active) {
        return <div>User deactivated</div>;
    }

    return (
        <div className="container">
            {userData && (
                <div className="user-details">
                    <h3>User Details:</h3>
                    <p>
                        <strong>First Name:</strong> {userData.firstName}
                    </p>
                    <p>
                        <strong>Surname:</strong> {userData.surname}
                    </p>
                    <p>
                        <strong>Email:</strong> {userData.emailAddress}
                    </p>
                    <p>
                        <strong>Role:</strong> {userData.role}
                    </p>
                    <p>
                        <strong>Client Type:</strong> {userData.clientType._clazz}
                    </p>
                    <p>
                        <strong>Current Rents:</strong> {userData.currentRents}
                    </p>
                    <p>
                        <button
                            onClick={() => handleNavigate()}
                        >
                            Edit Profile
                        </button>
                    </p>
                </div>
            )}
        </div>
    );
};
