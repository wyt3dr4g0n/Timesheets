export interface ISubCostCategory {
    id?: number;
    subCostCategoryDescription?: string;
}

export class SubCostCategory implements ISubCostCategory {
    constructor(public id?: number, public subCostCategoryDescription?: string) {}
}
