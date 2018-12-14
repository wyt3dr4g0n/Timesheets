export interface IProject {
    id?: number;
    projectId?: string;
    projectDescription?: string;
}

export class Project implements IProject {
    constructor(public id?: number, public projectId?: string, public projectDescription?: string) {}
}
