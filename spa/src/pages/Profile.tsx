import { useEffect, useState } from 'react';
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
    clientType: ClientType | undefined | null;
    currentRents: number | undefined | null;
}

export const UserProfile = () => {
    const [ModelUserData, setModelUserData] = useState<User | null>(null);
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
            setModelUserData(currentUser);
        }
    }, [currentUser]);

    if (currentUser == null) {
        return <div>ModelUser not logged</div>;
    }

    if (!currentUser.active) {
        return <div>ModelUser deactivated</div>;
    }

    return (
        <div className="container">
            {ModelUserData && (
                <div className="ModelUser-details">
                    <h3>ModelUser Details:</h3>
                    <p>
                        <strong>First Name:</strong> {ModelUserData.firstName}
                    </p>
                    <p>
                        <strong>Surname:</strong> {ModelUserData.surname}
                    </p>
                    <p>
                        <strong>Email:</strong> {ModelUserData.emailAddress}
                    </p>
                    <p>
                        <strong>Role:</strong> {ModelUserData.role}
                    </p>
                    <p>
                        <strong>Client Type:</strong> {ModelUserData.clientType?._clazz}
                    </p>
                    <p>
                        <strong>Current Rents:</strong> {ModelUserData.currentRents}
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
