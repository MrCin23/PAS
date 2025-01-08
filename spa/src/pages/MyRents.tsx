import { useEffect, useState } from 'react';
import { useUserSession } from '../model/UserContext';
import axios from "axios";

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

interface Rent {
    entityId: EntityId;
    client: User;
    vmachine: VMachine;
    beginTime: Date;
    endTime: Date | null;
    rentCost: number;
}

// interface EndRentForms {
//     endTime: string;
// }

export const MyRents = () => {
    const { currentUser } = useUserSession();
    const [rents, setRents] = useState<Rent[]>([]);

    useEffect(() => {
        // Fetch rents for the current user
        const fetchRents = async () => {
            if(currentUser != null) {
                try {
                    const response = await axios.get<Rent[]>(`/api/rent/all/client/${currentUser.entityId.uuid}`);
                    setRents(response.data);
                } catch (error) {
                    console.error('Error fetching rents:', error);
                }
            }
        };

        fetchRents();
    }, [currentUser]);

    const handleEndRent = async (rentId: string) => {
        // const endTime: EndRentForms = {
        //     endTime: new Date().toISOString(),
        // };
        try {
            // console.log(endTime);
            await axios.put(`/api/rent/end/${rentId}`); //, endTime
            // Update rents after ending
            setRents((prevRents) => prevRents.map(rent =>
                rent.entityId.uuid === rentId ? { ...rent, endTime: new Date() } : rent
            ));
        } catch (error) {
            console.error('Error ending rent:', error);
        }
    };
    
    if(currentUser == null) return <div>Musisz być zalogowany aby przeglądać tę witrynę!</div>;

    return (
        <div>
            <h1>My Rents</h1>
            {rents.length === 0 ? (
                <p>No rents found.</p>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>VM Name</th>
                        <th>RAM Size</th>
                        <th>CPU</th>
                        <th>Begin Time</th>
                        <th>End Time</th>
                        <th>Rent Cost</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {rents.map(rent => (
                        <tr key={rent.entityId.uuid}>
                            <td>{rent.vmachine.entityId.uuid}</td>
                            <td>{rent.vmachine.ramSize}</td>
                            <td>{`${rent.vmachine.cpunumber} - ${rent.vmachine.cpumanufacturer || 'Apple Arch'}`}</td>
                            <td>{new Date(rent.beginTime).toLocaleString()}</td>
                            <td>{rent.endTime ? new Date(rent.endTime).toLocaleString() : 'Active'}</td>
                            <td>{rent.rentCost}</td>
                            <td>
                                {!rent.endTime && (
                                    <button onClick={() => handleEndRent(rent.entityId.uuid)}>End Rent</button>
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};