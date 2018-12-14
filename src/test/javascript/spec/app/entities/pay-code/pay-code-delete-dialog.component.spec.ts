/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TimesheetsTestModule } from '../../../test.module';
import { PayCodeDeleteDialogComponent } from 'app/entities/pay-code/pay-code-delete-dialog.component';
import { PayCodeService } from 'app/entities/pay-code/pay-code.service';

describe('Component Tests', () => {
    describe('PayCode Management Delete Component', () => {
        let comp: PayCodeDeleteDialogComponent;
        let fixture: ComponentFixture<PayCodeDeleteDialogComponent>;
        let service: PayCodeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [PayCodeDeleteDialogComponent]
            })
                .overrideTemplate(PayCodeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PayCodeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayCodeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
