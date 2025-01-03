import {VMachine} from "./VMachine.ts";

export class AppleArch extends VMachine{

    constructor(id: string, CPUNumber: number, RAMSize: string, rented: boolean, actualrentalPrice: number) {
        super(id, CPUNumber, RAMSize, rented, actualrentalPrice);
    }
}