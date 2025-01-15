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


interface VMachine {
    entityId: EntityId;
    ramSize: string;
    isRented: boolean;
    actualRentalPrice: number;
    cpunumber: number;
    cpumanufacturer: string | null;
}

interface Rent {
    entityId: EntityId;
    client: User;
    vmachine: VMachine;
    beginTime: Date;
    endTime: Date | null;
    rentCost: number;
}
interface User {
    entityId: EntityId;
    firstName: string;
    surname: string;
    username: string;
    emailAddress: string;
    role: string;
    active: boolean;
    clientType: ClientType | undefined | null;
    currentRents: number | undefined | null;
}

function convertToDate(input: Date | Array<number>): Date {
    if (input instanceof Date) {
        return input;
    } else if (Array.isArray(input)) {
        const [year, month, day, hour = 0, minute = 0, second = 0, nanoseconds = 0] = input;
        const milliseconds = nanoseconds / 1e6;
        return new Date(year, month - 1, day, hour, minute, second, milliseconds);
    } else {
        throw new TypeError("Input must be a Date or an array of numbers.");
    }
}


export const ListUsers = () => {
    const [clients, setUsers] = useState<User[]>([]);
    const [username, setUsername] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [rents, setRents] = useState<Rent[] | null>(null);
    const [currentUser, setCurrentUser] = useState<User | null>(null);

    const handleChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const newUsername = e.target.value;
        setUsername(newUsername);

        try {
            if (newUsername === '') {
                const response = await axios.get<User[]>('https://flounder-sunny-goldfish.ngrok-free.app/REST/api/client',
                    {
                        headers: {
                            'ngrok-skip-browser-warning': '69420'
                        }
                    });
                setUsers(response.data);
                setError(null);
            } else {
                const response = await axios.get<User[]>(`https://flounder-sunny-goldfish.ngrok-free.app/REST/api/client/findClients/${newUsername}`,
                    {
                        headers: {
                            'ngrok-skip-browser-warning': '69420'
                        }
                    });
                if (response.data.length === 0) {
                    setError(`Nie znaleziono użytkownika o nazwie "${newUsername}".`);
                    setUsers([]);
                } else {
                    setUsers(response.data);
                    setError(null);
                }
            }
        } catch (err) {
            setError(`Wystąpił błąd podczas wyszukiwania użytkownika ${err}. Spróbuj ponownie później.`);
            setUsers([]);
        }
    };

    const activate = async (activate: boolean, entityId: string) => {
        try {
            if (activate) {
                const confirmation = window.confirm(
                    `Czy na pewno chcesz aktywować użytkownika o ID ${entityId}?`
                );
                if (!confirmation) return;
                await axios.put(`https://flounder-sunny-goldfish.ngrok-free.app/REST/api/client/activate/${entityId}`,
                    {
                        headers: {
                            'ngrok-skip-browser-warning': '69420'
                        }
                    });
                alert(`Klient o ID ${entityId} aktywowany!`);
            } else {
                const confirmation = window.confirm(
                    `Czy na pewno chcesz dezaktywować użytkownika o ID ${entityId}?`
                );
                if (!confirmation) return;
                await axios.put(`https://flounder-sunny-goldfish.ngrok-free.app/REST/api/client/deactivate/${entityId}`,
                    {
                        headers: {
                            'ngrok-skip-browser-warning': '69420'
                        }
                    });
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

    const fetchRents = async (user: User) => {
        try {
            const response = await axios.get<Rent[]>(`https://flounder-sunny-goldfish.ngrok-free.app/REST/api/rent/all/client/${user.entityId.uuid}`,
                {
                    headers: {
                        'ngrok-skip-browser-warning': '69420'
                    }
                });

            setRents(response.data.map(rent => ({ //here
                ...rent,
                beginTime: convertToDate(rent.beginTime),
                endTime: rent.endTime ? convertToDate(rent.endTime) : null,
            })));
            console.log(response.data)
            setCurrentUser(user);
        } catch (err) {
            alert(`Nie udało się pobrać listy wypożyczeń ${err}. Spróbuj ponownie później.`);
        }
    };

    const closeRentsModal = () => {
        setRents(null);
        setCurrentUser(null);
    };

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get<User[]>('https://flounder-sunny-goldfish.ngrok-free.app/REST/api/client',
                    {
                        headers: {
                            'ngrok-skip-browser-warning': '69420'
                        }
                    });
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

    return (
        <div>
            <h1>Lista Klientów</h1>
            <input
                type="text"
                id="username"
                name="username"
                placeholder="Search by username"
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
                    <th>Wypożyczenia</th>
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
                        <td>{client.clientType?.name}</td>
                        <td>
                            {client.active ? (
                                <button onClick={() => activate(false, client.entityId.uuid)}>
                                    Deaktywuj
                                </button>
                            ) : (
                                <button onClick={() => activate(true, client.entityId.uuid)}>
                                    Aktywuj
                                </button>
                            )}
                        </td>
                        <td>
                            <button onClick={() => fetchRents(client)}>Pokaż wypożyczenia</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            {rents && (
                <div className="rents-modal">
                    <div className="rents-modal-content">
                        <button className="close-btn" onClick={closeRentsModal}>
                            X
                        </button>
                        <h2>Wypożyczenia klienta {currentUser?.firstName} {currentUser?.surname}</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Maszyna</th>
                                <th>RAM Size</th>
                                <th>CPU</th>
                                <th>Data rozpoczęcia</th>
                                <th>Data zakończenia</th>
                            </tr>
                            </thead>
                            <tbody>
                            {rents.map((rent) => (
                                <tr key={rent.entityId.uuid}>
                                    <td>{rent.entityId.uuid}</td>
                                    <td>{rent.vmachine.entityId.uuid}</td>
                                    <td>{rent.vmachine.ramSize}</td>
                                    <td>{`${rent.vmachine.cpunumber} - ${rent.vmachine.cpumanufacturer || 'Apple Arch'}`}</td>
                                    <td>{new Date(rent.beginTime).toLocaleString()}</td>
                                    <td>{rent.endTime ? new Date(rent.endTime).toLocaleString() : 'Active'}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}
        </div>
    );
};
