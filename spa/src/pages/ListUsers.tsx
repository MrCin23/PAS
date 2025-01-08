import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './styles.css';


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

export const ListUsers = () => {
    const [clients, setUsers] = useState<User[]>([]);
    const [username, setUsername] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);


    const handleChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const newUsername = e.target.value;
        setUsername(newUsername);

        try {
            if (newUsername === '') {
                // Jeśli pole jest puste, pobierz wszystkich użytkowników
                const response = await axios.get<User[]>('/api/client');
                setUsers(response.data);
                setError(null); // Usunięcie ewentualnego komunikatu błędu
            } else {
                // Wyszukiwanie użytkownika
                const response = await axios.get<User[]>(`/api/client/findClients/${newUsername}`);
                if (response.data.length === 0) {
                    setError(`Nie znaleziono użytkownika o nazwie "${newUsername}".`);
                    setUsers([]); // Opróżnienie listy użytkowników
                } else {
                    setUsers(response.data);
                    setError(null); // Usunięcie ewentualnego komunikatu błędu
                }
            }
        } catch (err) {
            setError('Wystąpił błąd podczas wyszukiwania użytkownika. Spróbuj ponownie później.');
            setUsers([]); // Opróżnienie listy użytkowników w przypadku błędu
        }
    };

    const activate = async (activate: boolean, entityId: string) => {
        try {
            if(activate) {
                await axios.put(`/api/client/activate/${entityId}`);
                alert(`Klient o ID ${entityId} aktywowany!`);
            }
            else {
                await axios.put(`/api/client/deactivate/${entityId}`);
                alert(`Klient o ID ${entityId} deaktywowany!`);
            }
            setUsers((prev) =>
                prev.map((user) =>
                    user.entityId.uuid === entityId ? { ...user, active: activate } : user
                )
            );
        } catch (err) {
            console.error("Błąd przy deaktywowaniu użytkownika:", err);
            alert("Nie udało się deaktywować użytkownika. Spróbuj ponownie później.");
        }
    };

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get<User[]>('/api/client');
                setUsers(response.data);
                setLoading(false);
            } catch (err) {
                setError('Nie udało się pobrać listy klientów. Spróbuj ponownie później.' + err);
                setLoading(false);
            }
        };
        fetchUsers();
    }, []);

    if (loading) return <div>Ładowanie...</div>;
    // if (error) return <div>{error}</div>;

    return (
        <div>
            <h1>Lista Klientów</h1>
                {/*<label htmlFor="username">Search by username</label><br/>*/}
                <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder={"Search by username"}
                    value={username}
                    onChange={handleChange}
                />
            {error && <div className="error">{error}</div>}
            <table>
                <thead>
                <tr>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Nazwa użytkownika</th>
                    <th>Adres e-mail</th>
                    <th>Rola</th>
                    <th>Typ klienta</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {clients.map((client) => (
                    <tr key={client.entityId.uuid}>
                        <td>{client.firstName}</td>
                        <td>{client.surname}</td>
                        <td>{client.username}</td>
                        <td>{client.emailAddress}</td>
                        <td>{client.role}</td>
                        <td>{client.clientType.name}</td>
                        <td>
                            {client.active ? (
                                <button
                                    onClick={() => activate(false, client.entityId.uuid)}
                                >
                                    Deaktywuj
                                </button>
                            ) : (
                                <button
                                    onClick={() => activate(true, client.entityId.uuid)}
                                >
                                    Aktywuj
                                </button>
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

// export default ListUsers;
