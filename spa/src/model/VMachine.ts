export class VMachine {
    id: string
    CPUNumber: number
    RAMSize: string
    rented: boolean
    actualRentalPrice: number | undefined


    constructor(id: string, CPUNumber: number, RAMSize: string, rented: boolean, actualRentalPrice?: number) {
        this.id = id;
        this.CPUNumber = CPUNumber;
        this.RAMSize = RAMSize;
        this.rented = rented;
        this.actualRentalPrice = actualRentalPrice; //TODO 2 możliwości: 1. Jedna deklaracja dla tworzenia i druga do odczytu, 2. Konstruktor z opcjonalnym parametrem
    }
}