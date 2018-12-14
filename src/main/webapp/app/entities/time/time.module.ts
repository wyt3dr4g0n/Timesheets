import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesheetsSharedModule } from 'app/shared';
import {
    TimeComponent,
    TimeDetailComponent,
    TimeUpdateComponent,
    TimeDeletePopupComponent,
    TimeDeleteDialogComponent,
    timeRoute,
    timePopupRoute
} from './';

const ENTITY_STATES = [...timeRoute, ...timePopupRoute];

@NgModule({
    imports: [TimesheetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TimeComponent, TimeDetailComponent, TimeUpdateComponent, TimeDeleteDialogComponent, TimeDeletePopupComponent],
    entryComponents: [TimeComponent, TimeUpdateComponent, TimeDeleteDialogComponent, TimeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimesheetsTimeModule {}
