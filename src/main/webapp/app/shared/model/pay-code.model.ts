export interface IPayCode {
    id?: number;
    payCodeDescription?: string;
}

export class PayCode implements IPayCode {
    constructor(public id?: number, public payCodeDescription?: string) {}
}
