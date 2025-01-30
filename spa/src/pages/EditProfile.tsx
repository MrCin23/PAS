import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './styles.css';
import {jwtDecode} from "jwt-decode";

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
    admin = 'ADMIN',
    resourcemanager = 'RESOURCE_MANAGER',
    client = 'CLIENT',
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

export const EditProfile = () => {
    const [token] = useState<string | null>(localStorage.getItem('token'));
    const [userData, setUserData] = useState<User | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const [formData, setFormData] = useState({
        firstName: '',
        surname: '',
        emailAddress: '',
    });

    useEffect(() => {
        const fetchUserData = async () => {
            if (!token) return;

            try {
                const decodedToken: string = jwtDecode(token);
                const username = decodedToken.sub;
                try {
                    const response = await axios.get<User>(`/api/client/findClient/${username}`, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'ngrok-skip-browser-warning': '69420',
                        },
                    });
                    setUserData(response.data);
                    setFormData({
                        firstName: response.data.firstName,
                        surname: response.data.surname,
                        emailAddress: response.data.emailAddress,
                    });
                    setLoading(false);
                } catch (err) {
                    setError(`Nie udało się pobrać danych użytkownika ${err}.`);
                    setLoading(false);
                }
            } catch (error) {
                setError(`Token decoding failed: ${error}`);
                setLoading(false);
            }
        };

        fetchUserData();
    }, [token]);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleFormSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!token) {
            alert('Musisz być zalogowany, aby edytować swój profil.');
            return;
        }
        const confirmation = window.confirm(`Czy na pewno chcesz zmienić dane?`);
        if (!confirmation) return;
        try {
            await axios.put(
                `/api/client/${userData?.entityId.uuid}`,
                formData,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'ngrok-skip-browser-warning': '69420',
                    },
                }
            );
            alert('Dane zostały zaktualizowane!');
            await axios.get<User>(
                `/api/client/${userData?.entityId.uuid}`,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'ngrok-skip-browser-warning': '69420',
                    },
                }
            );
        } catch (err) {
            console.error('Błąd przy aktualizacji danych:', err);
            alert('Nie udało się zaktualizować danych. Spróbuj ponownie.');
        }
    };

    if (loading) return <div>Ładowanie danych...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="container">
            <h1 className="text-center mb-4">Edycja profilu</h1>
            {userData && (
                <form className="edit-profile-form" onSubmit={handleFormSubmit}>
                    <div className="mb-3">
                        <label htmlFor="firstName" className="form-label">
                            Imię:
                        </label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleInputChange}
                            className="form-control"
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="surname" className="form-label">
                            Nazwisko:
                        </label>
                        <input
                            type="text"
                            id="surname"
                            name="surname"
                            value={formData.surname}
                            onChange={handleInputChange}
                            className="form-control"
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="emailAddress" className="form-label">
                            Adres e-mail:
                        </label>
                        <input
                            type="email"
                            id="emailAddress"
                            name="emailAddress"
                            value={formData.emailAddress}
                            onChange={handleInputChange}
                            className="form-control"
                            required
                        />
                    </div>
                    <button className="btn btn-primary w-100" type="submit">
                        Zapisz zmiany
                    </button>
                </form>
            )}
        </div>
    );
};
