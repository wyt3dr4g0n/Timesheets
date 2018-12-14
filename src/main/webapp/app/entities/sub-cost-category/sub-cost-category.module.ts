import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesheetsSharedModule } from 'app/shared';
import {
    SubCostCategoryComponent,
    SubCostCategoryDetailComponent,
    SubCostCategoryUpdateComponent,
    SubCostCategoryDeletePopupComponent,
    SubCostCategoryDeleteDialogComponent,
    subCostCategoryRoute,
    subCostCategoryPopupRoute
} from './';

const ENTITY_STATES = [...subCostCategoryRoute, ...subCostCategoryPopupRoute];

@NgModule({
    imports: [TimesheetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SubCostCategoryComponent,
        SubCostCategoryDetailComponent,
        SubCostCategoryUpdateComponent,
        SubCostCategoryDeleteDialogComponent,
        SubCostCategoryDeletePopupComponent
    ],
    entryComponents: [
        SubCostCategoryComponent,
        SubCostCategoryUpdateComponent,
        SubCostCategoryDeleteDialogComponent,
        SubCostCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimesheetsSubCostCategoryModule {}
