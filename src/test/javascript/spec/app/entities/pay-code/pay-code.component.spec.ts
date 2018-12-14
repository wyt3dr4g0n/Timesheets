/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TimesheetsTestModule } from '../../../test.module';
import { PayCodeComponent } from 'app/entities/pay-code/pay-code.component';
import { PayCodeService } from 'app/entities/pay-code/pay-code.service';
import { PayCode } from 'app/shared/model/pay-code.model';

describe('Component Tests', () => {
    describe('PayCode Management Component', () => {
        let comp: PayCodeComponent;
        let fixture: ComponentFixture<PayCodeComponent>;
        let service: PayCodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [PayCodeComponent],
                providers: []
            })
                .overrideTemplate(PayCodeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PayCodeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayCodeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PayCode(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.payCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
