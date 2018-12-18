import { IProject } from 'app/shared/model//project.model';

export interface ITask {
    id?: number;
    taskId?: string;
    taskDescription?: string;
    projectId?: IProject;
}

export class Task implements ITask {
    constructor(public id?: number, public taskId?: string, public taskDescription?: string, public projectId?: IProject) {}
}
