import {User} from "./User.ts";
import {VMachine} from "./VMachine.ts";

export class Rent {
    id: string
    client: User
    vMachine: VMachine
    beginTime: Date
    endTime: Date | undefined
    rentCost: number | undefined

    constructor(id: string, client: User, vMachine: VMachine, beginTime: Date) {
        this.id = id;
        this.client = client;
        this.vMachine = vMachine;
        this.beginTime = beginTime;
        this.endTime = undefined
        this.rentCost = undefined
    }
}