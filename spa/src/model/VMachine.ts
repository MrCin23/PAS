export class VMachine {
    id: string
    CPUNumber: number
    RAMSize: string
    rented: boolean
    actualrentalPrice: number | undefined


    constructor(id: string, CPUNumber: number, RAMSize: string, rented: boolean, actualrentalPrice?: number) {
        this.id = id;
        this.CPUNumber = CPUNumber;
        this.RAMSize = RAMSize;
        this.rented = rented;
        this.actualrentalPrice = actualrentalPrice; //TODO 2 możliwości: 1. Jedna deklaracja dla tworzenia i druga do odczytu, 2. Konstruktor z opcjonalnym parametrem
    }
}