import React, { useState } from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import { useUserSession } from '../model/UserContext';


enum Role {
    admin = "ADMIN",
    moderator = "RESOURCE_MANAGER",
    client = "CLIENT",
}
//
// interface EntityId {
//     uuid: string;
// }

interface ClientType {
    _clazz: "standard" | "premium";
}

interface FormData {
    _clazz: string;
    // entityId: EntityId;
    firstName: string;
    surname: string;
    username: string;
    emailAddress: string;
    role: Role;
    active: string;
    clientType: ClientType;
}

export const CreateUser = () => { //export const CreateUser: React.FC = () => {
    const navigate = useNavigate();
    const { currentUser } = useUserSession();
    const [formData, setFormData] = useState<FormData>({
        // entityId: { uuid: uuidv4() },
        _clazz: 'Client',
        firstName: '',
        surname: '',
        username: '',
        emailAddress: '',
        role: Role.client,
        active: true.toString(),
        clientType: {_clazz: "standard"},
    });

    const [formErrors, setFormErrors] = useState<{ [key: string]: string }>({});
    const [notification, setNotification] = useState<string | null>(null);

    const validate = (): boolean => {
        const errors: { [key: string]: string } = {};

        if (!formData.firstName.trim()) {
            errors.firstName = 'First name is required';
        }
        if (!formData.surname.trim()) {
            errors.surname = 'Last name is required';
        }
        if (!formData.username.trim()) {
            errors.username = 'Username is required';
        }
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!formData.emailAddress.trim()) {
            errors.emailAddress = 'Email is required';
        } else if (!emailRegex.test(formData.emailAddress)) {
            errors.emailAddress = 'Invalid email address';
        }

        setFormErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;

        const updatedFormData = { ...formData, [name]: value };

        if (name === "_clazz") {
            switch (value) {
                case "Admin":
                    updatedFormData.role = Role.admin;
                    break;
                case "ResourceManager":
                    updatedFormData.role = Role.moderator;
                    break;
                default:
                    updatedFormData.role = Role.client;
            }
        }

        setFormData(updatedFormData);
    };


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!validate()) {
            setNotification('There are errors in the form.');
            return;
        }

        try {
            console.log(formData);
            await axios.post('https://flounder-sunny-goldfish.ngrok-free.app/REST/api/client', formData,
                {
                    headers: {
                        'ngrok-skip-browser-warning': '69420'
                    }
                });
            setNotification('User registered successfully!');
            setFormData({
                _clazz: 'Client',
                // entityId: { uuid: uuidv4() },
                firstName: '',
                surname: '',
                username: '',
                emailAddress: '',
                role: Role.client,
                active: true.toString(),
                clientType: {_clazz: "standard"},
            });
            setFormErrors({});
        } catch (error) {
            console.log(error);
            navigate("/error", { state: { error } });
        }
    };

    if(currentUser != undefined && currentUser.role == "ADMIN") {
        return (
            <div style={{ maxWidth: '400px', margin: '0 auto' }}>
                <h2>User Registration</h2>
                {notification && <p style={{ color: notification.includes('success') ? 'green' : 'red' }}>{notification}</p>}
                <form onSubmit={handleSubmit}>
                    <div style={{marginBottom: '10px'}}>
                        <label htmlFor="firstName">First Name</label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleChange}
                            style={{display: 'block', width: '100%', padding: '8px'}}
                        />
                        {formErrors.firstName && <span style={{color: 'red'}}>{formErrors.firstName}</span>}
                    </div>

                    <div style={{marginBottom: '10px'}}>
                        <label htmlFor="surname">Surname</label>
                        <input
                            type="text"
                            id="surname"
                            name="surname"
                            value={formData.surname}
                            onChange={handleChange}
                            style={{display: 'block', width: '100%', padding: '8px'}}
                        />
                        {formErrors.surname && <span style={{color: 'red'}}>{formErrors.surname}</span>}
                    </div>

                    <div style={{marginBottom: '10px'}}>
                        <label htmlFor="username">Username</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            style={{display: 'block', width: '100%', padding: '8px'}}
                        />
                        {formErrors.username && <span style={{color: 'red'}}>{formErrors.username}</span>}
                    </div>

                    <div style={{marginBottom: '10px'}}>
                        <label htmlFor="emailAddress">Email address</label>
                        <input
                            type="email"
                            id="emailAddress"
                            name="emailAddress"
                            value={formData.emailAddress}
                            onChange={handleChange}
                            style={{display: 'block', width: '100%', padding: '8px'}}
                        />
                        {formErrors.emailAddress && <span style={{color: 'red'}}>{formErrors.emailAddress}</span>}
                    </div>
                    <div>
                        <select
                            id="_clazz"
                            name="_clazz"
                            value={formData._clazz}
                            onChange={handleChange}
                            style={{padding: '8px', width: '40%'}}
                        >
                            <option value="Client">Client</option>
                            <option value="ResourceManager">ResourceManager</option>
                            <option value="Admin">Admin</option>
                        </select>
                    </div>
                    <button type="submit" style={{padding: '10px 20px'}}>
                        Register
                    </button>
                </form>
            </div>
        );
    }

    return (
        <div style={{maxWidth: '400px', margin: '0 auto'}}>
            <h2>User Registration</h2>
            {notification && <p style={{color: notification.includes('success') ? 'green' : 'red'}}>{notification}</p>}
            <form onSubmit={handleSubmit}>
                <div style={{marginBottom: '10px'}}>
                    <label htmlFor="firstName">First Name</label>
                    <input
                        type="text"
                        id="firstName"
                        name="firstName"
                        value={formData.firstName}
                        onChange={handleChange}
                        style={{display: 'block', width: '100%', padding: '8px'}}
                    />
                    {formErrors.firstName && <span style={{color: 'red'}}>{formErrors.firstName}</span>}
                </div>

                <div style={{marginBottom: '10px'}}>
                    <label htmlFor="surname">Surname</label>
                    <input
                        type="text"
                        id="surname"
                        name="surname"
                        value={formData.surname}
                        onChange={handleChange}
                        style={{display: 'block', width: '100%', padding: '8px'}}
                    />
                    {formErrors.surname && <span style={{color: 'red'}}>{formErrors.surname}</span>}
                </div>

                <div style={{marginBottom: '10px'}}>
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        style={{display: 'block', width: '100%', padding: '8px'}}
                    />
                    {formErrors.username && <span style={{color: 'red'}}>{formErrors.username}</span>}
                </div>

                <div style={{marginBottom: '10px'}}>
                    <label htmlFor="emailAddress">Email address</label>
                    <input
                        type="email"
                        id="emailAddress"
                        name="emailAddress"
                        value={formData.emailAddress}
                        onChange={handleChange}
                        style={{display: 'block', width: '100%', padding: '8px'}}
                    />
                    {formErrors.emailAddress && <span style={{color: 'red'}}>{formErrors.emailAddress}</span>}
                </div>

                <button type="submit" style={{padding: '10px 20px'}}>
                    Register
                </button>
            </form>
        </div>
    );
};

// export default CreateUser;