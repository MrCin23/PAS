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

const ListUsers: React.FC = () => {
    const [clients, setUsers] = useState<User[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

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
    if (error) return <div>{error}</div>;

    return (
        <div>
            <h1>Lista Klientów</h1>
            <table>
                <thead>
                <tr>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Nazwa użytkownika</th>
                    <th>Adres e-mail</th>
                    <th>Rola</th>
                    <th>Typ klienta</th>
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
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ListUsers;
