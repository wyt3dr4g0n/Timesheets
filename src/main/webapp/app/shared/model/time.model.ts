import { Moment } from 'moment';
import { IProject } from 'app/shared/model//project.model';
import { ITask } from 'app/shared/model//task.model';
import { ISubCostCategory } from 'app/shared/model//sub-cost-category.model';
import { IPayCode } from 'app/shared/model//pay-code.model';

export interface ITime {
    id?: number;
    date?: Moment;
    cost?: number;
    forBilling?: number;
    forPayroll?: number;
    billingDescription?: string;
    description?: string;
    attachmentsContentType?: string;
    attachments?: any;
    notes?: string;
    projectId?: IProject;
    taskId?: ITask;
    subCostCategory?: ISubCostCategory;
    payCode?: IPayCode;
}

export class Time implements ITime {
    constructor(
        public id?: number,
        public date?: Moment,
        public cost?: number,
        public forBilling?: number,
        public forPayroll?: number,
        public billingDescription?: string,
        public description?: string,
        public attachmentsContentType?: string,
        public attachments?: any,
        public notes?: string,
        public projectId?: IProject,
        public taskId?: ITask,
        public subCostCategory?: ISubCostCategory,
        public payCode?: IPayCode
    ) {}
}
