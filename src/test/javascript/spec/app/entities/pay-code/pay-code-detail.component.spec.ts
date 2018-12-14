/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimesheetsTestModule } from '../../../test.module';
import { PayCodeDetailComponent } from 'app/entities/pay-code/pay-code-detail.component';
import { PayCode } from 'app/shared/model/pay-code.model';

describe('Component Tests', () => {
    describe('PayCode Management Detail Component', () => {
        let comp: PayCodeDetailComponent;
        let fixture: ComponentFixture<PayCodeDetailComponent>;
        const route = ({ data: of({ payCode: new PayCode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [PayCodeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PayCodeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PayCodeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.payCode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
