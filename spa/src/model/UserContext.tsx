import React, { createContext, useContext, useState } from 'react';

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


const UserContext = createContext<{
    currentUser: User | null;
    setCurrentUser: (user: User) => void;
    clearUser: () => void;
}>({
    currentUser: null,
    setCurrentUser: () => {},
    clearUser: () => {},
});

export const UserProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [currentUser, setCurrentUserState] = useState<User | null>(null);

    const setCurrentUser = (user: User) => {
        setCurrentUserState(user);
    };

    const clearUser = () => {
        setCurrentUserState(null);
    };

    return (
        <UserContext.Provider value={{ currentUser, setCurrentUser, clearUser }}>
            {children}
        </UserContext.Provider>
    );
};

// Hook do ułatwienia dostępu do kontekstu
export const useUserSession = () => {
    return useContext(UserContext);
};
