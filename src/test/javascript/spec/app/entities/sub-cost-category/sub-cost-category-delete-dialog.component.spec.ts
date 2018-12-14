/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TimesheetsTestModule } from '../../../test.module';
import { SubCostCategoryDeleteDialogComponent } from 'app/entities/sub-cost-category/sub-cost-category-delete-dialog.component';
import { SubCostCategoryService } from 'app/entities/sub-cost-category/sub-cost-category.service';

describe('Component Tests', () => {
    describe('SubCostCategory Management Delete Component', () => {
        let comp: SubCostCategoryDeleteDialogComponent;
        let fixture: ComponentFixture<SubCostCategoryDeleteDialogComponent>;
        let service: SubCostCategoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [SubCostCategoryDeleteDialogComponent]
            })
                .overrideTemplate(SubCostCategoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SubCostCategoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubCostCategoryService);
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
