import { useEffect, useState } from 'react';
import './styles.css';
import { useUserSession } from '../model/UserContext';

interface EntityId {
    uuid: string;
}

interface ClientType {
    _clazz: string;
    entityId: EntityId;
    maxRentedMachines: number;
    name: string;
}

interface User {
    entityId: EntityId;
    firstName: string;
    surname: string;
    username: string;
    emailAddress: string;
    role: string;
    active: boolean;
    clientType: ClientType;
    currentRents: number;
}

export const UserProfile = () => {
    const [userData, setUserData] = useState<User | null>(null);
    const { currentUser } = useUserSession();

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
                </div>
            )}
        </div>
    );
};
