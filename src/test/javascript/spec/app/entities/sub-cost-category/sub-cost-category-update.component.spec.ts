/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TimesheetsTestModule } from '../../../test.module';
import { SubCostCategoryUpdateComponent } from 'app/entities/sub-cost-category/sub-cost-category-update.component';
import { SubCostCategoryService } from 'app/entities/sub-cost-category/sub-cost-category.service';
import { SubCostCategory } from 'app/shared/model/sub-cost-category.model';

describe('Component Tests', () => {
    describe('SubCostCategory Management Update Component', () => {
        let comp: SubCostCategoryUpdateComponent;
        let fixture: ComponentFixture<SubCostCategoryUpdateComponent>;
        let service: SubCostCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [SubCostCategoryUpdateComponent]
            })
                .overrideTemplate(SubCostCategoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SubCostCategoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubCostCategoryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SubCostCategory(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.subCostCategory = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SubCostCategory();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.subCostCategory = entity;
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
