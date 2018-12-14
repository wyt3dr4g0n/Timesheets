import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesheetsSharedModule } from 'app/shared';
import {
    PayCodeComponent,
    PayCodeDetailComponent,
    PayCodeUpdateComponent,
    PayCodeDeletePopupComponent,
    PayCodeDeleteDialogComponent,
    payCodeRoute,
    payCodePopupRoute
} from './';

const ENTITY_STATES = [...payCodeRoute, ...payCodePopupRoute];

@NgModule({
    imports: [TimesheetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PayCodeComponent,
        PayCodeDetailComponent,
        PayCodeUpdateComponent,
        PayCodeDeleteDialogComponent,
        PayCodeDeletePopupComponent
    ],
    entryComponents: [PayCodeComponent, PayCodeUpdateComponent, PayCodeDeleteDialogComponent, PayCodeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimesheetsPayCodeModule {}
