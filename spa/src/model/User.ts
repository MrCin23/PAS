export class User {
    id: string;
    firstName: string
    surname: string
    username: string
    emailAddress: string
    // password: string
    role: string //todo ROLE
    active: boolean


    constructor(id: string, firstName: string, surname: string, username: string, emailAddress: string) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.active = false; //TODO przemyśleć czy nie dać default true
        this.role = "Client"; //TODO Admin i ResourceManager
    }

    public toString(): string {
        return `User ${this.firstName} ${this.surname} ${this.username} ${this.emailAddress}, status: ${this.active ? "active" : "inactive"}, Role: ${this.role}`;
    }
}
