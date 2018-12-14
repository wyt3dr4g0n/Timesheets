import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TimesheetsTaskModule } from './task/task.module';
import { TimesheetsProjectModule } from './project/project.module';
import { TimesheetsSubCostCategoryModule } from './sub-cost-category/sub-cost-category.module';
import { TimesheetsPayCodeModule } from './pay-code/pay-code.module';
import { TimesheetsTimeModule } from './time/time.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TimesheetsTaskModule,
        TimesheetsProjectModule,
        TimesheetsSubCostCategoryModule,
        TimesheetsPayCodeModule,
        TimesheetsTimeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimesheetsEntityModule {}
