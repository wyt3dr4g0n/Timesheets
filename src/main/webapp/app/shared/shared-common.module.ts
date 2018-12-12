import { NgModule } from '@angular/core';

import { TimesheetsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TimesheetsSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TimesheetsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TimesheetsSharedCommonModule {}
