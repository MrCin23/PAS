import {VMachine} from "./VMachine.ts";

export class x86 extends VMachine{
    CPUManufacturer: string

    constructor(id: string, CPUNumber: number, RAMSize: string, rented: boolean, actualRentalPrice: number, CPUManufacturer: string) {
        super(id, CPUNumber, RAMSize, rented, actualRentalPrice);
        this.CPUManufacturer = CPUManufacturer;
    }
}