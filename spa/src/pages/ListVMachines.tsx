import { useEffect, useState } from 'react';
import axios from 'axios';
import './styles.css';
import { useUserSession } from '../model/UserContext';

interface EntityId {
    uuid: string;
}

interface VMachine {
    entityId: EntityId;
    ramSize: string;
    isRented: boolean;
    actualRentalPrice: number;
    cpunumber: number;
    cpumanufacturer: string | null;
}

interface Rent{
    clientId: string;
    vmId: string;
    beginTime: string;
}

export const ListVMachines = () => {
    const [vMachines, setvMachines] = useState<VMachine[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const {currentUser} = useUserSession();

    const handleRent = async (vmId: string) => {
        if (!currentUser) {
            alert("Musisz być zalogowany, aby wypożyczyć maszynę!");
            return;
        }
        const confirmRent = window.confirm(
            `Czy na pewno chcesz wypożyczyć maszynę o ID ${vmId}?`
        );

        if (!confirmRent) return;
        const rent: Rent = {
            clientId: currentUser.entityId.uuid, // Użyj ID aktualnego użytkownika
            vmId,
            beginTime: new Date().toUTCString(), // Ustaw aktualny czas
        };

        try {
            console.log(rent);
            await axios.post('https://flounder-sunny-goldfish.ngrok-free.app/REST/api/rent', rent,
                {
                    headers: {
                        'ngrok-skip-browser-warning': '69420'
                    }
                });
            alert(`Maszyna o ID ${vmId} została wypożyczona!`);
            setvMachines((prev) =>
                prev.map((vm) =>
                    vm.entityId.uuid === vmId ? { ...vm, isRented: true } : vm
                )
            );
        } catch (err) {
            console.error("Błąd przy wypożyczaniu maszyny:", err);
            alert("Nie udało się wypożyczyć maszyny. Spróbuj ponownie później.");
        }
    };

    const deleteVMachine = async (vmId: string) => {
        const confirmDeletion = window.confirm(
            `Czy na pewno chcesz usunąć maszynę o ID ${vmId}?`
        );
        if (!confirmDeletion) return;
        try {
            await axios.delete(`https://flounder-sunny-goldfish.ngrok-free.app/REST/api/vmachine/${vmId}`,
                {
                    headers: {
                        'ngrok-skip-browser-warning': '69420'
                    }
                });
            alert(`Maszyna o ID ${vmId} została usunięta!`);
            setvMachines((prev) =>
                prev.filter((vm) => vm.entityId.uuid !== vmId)
            );
        } catch (err) {
            console.error("Błąd przy usuwaniu maszyny:", err);
            alert("Nie udało się usunąć maszyny. Spróbuj ponownie później.");
        }
    };

    useEffect(() => {
        const fetchVMachines = async () => {
            try {
                const response = await axios.get<VMachine[]>('https://flounder-sunny-goldfish.ngrok-free.app/REST/api/vmachine',
                    {
                        headers: {
                            'ngrok-skip-browser-warning': '69420'
                        }
                    });
                setvMachines(response.data);
                setLoading(false);
            } catch (err) {
                setError("Nie udało się pobrać listy maszyn wirtualnych. Spróbuj ponownie później." + err);
                setLoading(false);
            }
        }

        fetchVMachines();
    }, [])

    if(loading) return <div>Ładowanie...</div>;
    if (error) return <div>{error}</div>;

    if(currentUser == null) return <div>Musisz być zalogowany, aby móc przeglądać tą zawartość</div> //TODO można dorobić tutaj coś takiego jak przekierowanie na stronę główną, a to wyświetlać jako alert

    if(currentUser.role == "ADMIN") {
        return <div>Nie masz uprawnień do przeglądania tej witryny!</div>
    }

    if(currentUser.role == "RESOURCE_MANAGER") {
        return (
            <div>
                <h1>Lista maszyn wirtualnych</h1>
                <table>
                    <thead>
                    <tr>
                        <th>RAM</th>
                        <th>Ilość jednostek przetwarzających</th>
                        <th>Producent procesora</th>
                        <th>Status</th>
                        <th>Cena</th>
                    </tr>
                    </thead>
                    <tbody>
                    {vMachines.map((vMachine) => (
                        <tr key={vMachine.entityId.uuid}>
                            <td>{vMachine.ramSize}</td>
                            <td>{vMachine.cpunumber}</td>
                            <td>{vMachine.cpumanufacturer}</td>
                            <td>
                                {vMachine.isRented ? (
                                    <span>Wypożyczona</span>
                                ) : (
                                    <button
                                        onClick={() => deleteVMachine(vMachine.entityId.uuid)}
                                    >
                                        Usuń
                                    </button>
                                )}
                            </td>
                            <td>{vMachine.actualRentalPrice}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        )
    }

    if(currentUser.role == "CLIENT") {
        return (
            <div>
                <h1>Lista maszyn wirtualnych</h1>
                <table>
                    <thead>
                    <tr>
                        <th>RAM</th>
                        <th>Ilość jednostek przetwarzających</th>
                        <th>Producent procesora</th>
                        <th>Status</th>
                        <th>Cena</th>
                    </tr>
                    </thead>
                    <tbody>
                    {vMachines.map((vMachine) => (
                        <tr key={vMachine.entityId.uuid}>
                            <td>{vMachine.ramSize}</td>
                            <td>{vMachine.cpunumber}</td>
                            <td>{vMachine.cpumanufacturer}</td>
                            <td>
                                {vMachine.isRented ? (
                                    <span>Wypożyczona</span>
                                ) : (
                                    <button
                                        onClick={() => handleRent(vMachine.entityId.uuid)}
                                    >
                                        Wypożycz
                                    </button>
                                )}
                            </td>
                            <td>{vMachine.actualRentalPrice}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        )
    }

    return <div>xDDD</div>
}

