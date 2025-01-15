import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface AppleArch {
    ramSize: string;
    cpunumber: number;
    _clazz: string;
}

interface x86 {
    ramSize: string;
    cpunumber: number;
    cpumanufacturer: string;
    _clazz: string;
}

type FormData = AppleArch | x86;

export const CreateVMachine = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState<FormData>({
        ramSize: '',
        cpunumber: 0,
        _clazz: 'applearch', // Domyślny typ
        cpumanufacturer: '', // Tylko dla x86
    });

    const [ramAmount, setRamAmount] = useState<number>(0);
    const [ramUnit, setRamUnit] = useState<string>('GB'); // Domyślna jednostka
    const [formErrors, setFormErrors] = useState<{ [key: string]: string }>({});
    const [notification, setNotification] = useState<string | null>(null);

    const validate = (): boolean => {
        const errors: { [key: string]: string } = {};

        if (ramAmount <= 0) {
            errors.ramSize = 'RAM amount must be greater than 0.';
        }

        if (formData.cpunumber <= 0) {
            errors.cpunumber = 'CPU number must be greater than 0.';
        }

        if (formData._clazz === 'x86' && !('cpumanufacturer' in formData && formData.cpumanufacturer)) {
            errors.cpumanufacturer = 'CPU manufacturer is required for x86.';
        }

        setFormErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;

        if (name === 'ramAmount') {
            setRamAmount(Number(value));
        } else if (name === 'ramUnit') {
            setRamUnit(value);
        } else {
            setFormData({ ...formData, [name]: value });
        }
    };

    const handleTypeChange = (type: 'applearch' | 'x86') => {
        setFormData({
            ramSize: '',
            cpunumber: 0,
            _clazz: type,
            ...(type === 'x86' ? { cpumanufacturer: '' } : {}),
        });
        setFormErrors({});
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!validate()) {
            setNotification('There are errors in the form.');
            return;
        }

        // Połącz ilość RAM i jednostkę
        const ramSize = `${ramAmount}${ramUnit}`;
        const payload = { ...formData, ramSize };

        try {
            await axios.post('https://flounder-sunny-goldfish.ngrok-free.app/REST/api/vmachine', payload,
                {
                    headers: {
                        'ngrok-skip-browser-warning': '69420'
                    }
                });
            setNotification('VMachine registered successfully!');
            setFormData({
                ramSize: '',
                cpunumber: 0,
                _clazz: 'applearch',
            });
            setRamAmount(0);
            setRamUnit('GB');
            setFormErrors({});
        } catch (error) {
            console.error(error);
            navigate('/error', { state: { error } });
        }
    };

    return (
        <div style={{ maxWidth: '400px', margin: '0 auto' }}>
            <h2>VMachine Registration</h2>
            {notification && <p style={{ color: notification.includes('success') ? 'green' : 'red' }}>{notification}</p>}
            <div>
                <button onClick={() => handleTypeChange('applearch')}>AppleArch</button>
                <button onClick={() => handleTypeChange('x86')}>x86</button>
            </div>
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="ramAmount">RAM</label>
                    <div style={{ display: 'flex', gap: '10px' }}>
                        <input
                            type="number"
                            id="ramAmount"
                            name="ramAmount"
                            value={ramAmount}
                            onChange={handleChange}
                            style={{ padding: '8px', width: '60%' }}
                        />
                        <select
                            id="ramUnit"
                            name="ramUnit"
                            value={ramUnit}
                            onChange={handleChange}
                            style={{ padding: '8px', width: '40%' }}
                        >
                            <option value="MB">MB</option>
                            <option value="GB">GB</option>
                            <option value="TB">TB</option>
                        </select>
                    </div>
                    {formErrors.ramSize && <span style={{ color: 'red' }}>{formErrors.ramSize}</span>}
                </div>

                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="cpunumber">CPU Number</label>
                    <input
                        type="number"
                        id="cpunumber"
                        name="cpunumber"
                        value={formData.cpunumber}
                        onChange={handleChange}
                        style={{ display: 'block', width: '100%', padding: '8px' }}
                    />
                    {formErrors.cpunumber && <span style={{ color: 'red' }}>{formErrors.cpunumber}</span>}
                </div>

                {formData._clazz === 'x86' && (
                    <div style={{ marginBottom: '10px' }}>
                        <label htmlFor="cpumanufacturer">CPU Manufacturer</label>
                        <input
                            type="text"
                            id="cpumanufacturer"
                            name="cpumanufacturer"
                            value={(formData as x86).cpumanufacturer || ''}
                            onChange={handleChange}
                            style={{ display: 'block', width: '100%', padding: '8px' }}
                        />
                        {formErrors.cpumanufacturer && <span style={{ color: 'red' }}>{formErrors.cpumanufacturer}</span>}
                    </div>
                )}

                <button type="submit" style={{ padding: '10px 20px' }}>
                    Register
                </button>
            </form>
        </div>
    );
};
