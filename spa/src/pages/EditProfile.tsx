import React, { useEffect, useState } from 'react';
import { useUserSession } from '../model/UserContext';
import axios from "axios";
import "./styles.css"

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

export const EditProfile = () => {
    const { currentUser, setCurrentUser } = useUserSession();
    const [userData, setUserData] = useState<User | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const [formData, setFormData] = useState({
        firstName: "",
        surname: "",
        emailAddress: ""
    });

    useEffect(() => {
        const fetchUserData = async () => {
            if (!currentUser) return;

            try {
                const response = await axios.get<User>(`/api/client/${currentUser.entityId.uuid}`);
                setUserData(response.data);
                setFormData({
                    firstName: response.data.firstName,
                    surname: response.data.surname,
                    emailAddress: response.data.emailAddress
                });
                setLoading(false);
            } catch (err) {
                setError("Nie udało się pobrać danych użytkownika.");
                setLoading(false);
            }
        };

        fetchUserData();
    }, [currentUser]);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleFormSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!currentUser) {
            alert("Musisz być zalogowany, aby edytować swój profil.");
            return;
        }

        try {
            await axios.put(`/api/client/${currentUser.entityId.uuid}`, formData);
            alert("Dane zostały zaktualizowane!");
            const response = await axios.get<User>(`/api/client/${currentUser.entityId.uuid}`)
            setCurrentUser(response.data);
        } catch (err) {
            console.error("Błąd przy aktualizacji danych:", err);
            alert("Nie udało się zaktualizować danych. Spróbuj ponownie.");
        }
    };

    if (loading) return <div>Ładowanie danych...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="container">
            <h1 className="title">Edycja profilu</h1>
            {userData && (
                <form className="edit-profile-form" onSubmit={handleFormSubmit}>
                    <div className="form-group">
                        <label htmlFor="firstName">Imię:</label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="surname">Nazwisko:</label>
                        <input
                            type="text"
                            id="surname"
                            name="surname"
                            value={formData.surname}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="emailAddress">Adres e-mail:</label>
                        <input
                            type="email"
                            id="emailAddress"
                            name="emailAddress"
                            value={formData.emailAddress}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <button className="save-button" type="submit">Zapisz zmiany</button>
                </form>
            )}
        </div>
    );
};
