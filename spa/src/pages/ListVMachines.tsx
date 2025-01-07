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
    beginTime: Date;
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

        const rent: Rent = {
            clientId: currentUser.entityId.uuid, // Użyj ID aktualnego użytkownika
            vmId,
            beginTime: new Date(), // Ustaw aktualny czas
        };

        try {
            await axios.post('/api/rent', rent);
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

    useEffect(() => {
        const fetchVMachines = async () => {
            try {
                const response = await axios.get<VMachine[]>('/api/vmachine');
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
                        {/*TODO prawdopodobnie trzeba zrobić dwa niemal identyczne widoki tutaj, ale jeden jest z przyciskiem usuń, a drugi z przyciskiem wypożycz*/}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}