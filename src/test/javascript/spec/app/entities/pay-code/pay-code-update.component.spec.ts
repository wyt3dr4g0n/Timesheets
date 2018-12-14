/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TimesheetsTestModule } from '../../../test.module';
import { PayCodeUpdateComponent } from 'app/entities/pay-code/pay-code-update.component';
import { PayCodeService } from 'app/entities/pay-code/pay-code.service';
import { PayCode } from 'app/shared/model/pay-code.model';

describe('Component Tests', () => {
    describe('PayCode Management Update Component', () => {
        let comp: PayCodeUpdateComponent;
        let fixture: ComponentFixture<PayCodeUpdateComponent>;
        let service: PayCodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [PayCodeUpdateComponent]
            })
                .overrideTemplate(PayCodeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PayCodeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayCodeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PayCode(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.payCode = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PayCode();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.payCode = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
