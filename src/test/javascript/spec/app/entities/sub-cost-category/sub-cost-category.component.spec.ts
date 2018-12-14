/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TimesheetsTestModule } from '../../../test.module';
import { SubCostCategoryComponent } from 'app/entities/sub-cost-category/sub-cost-category.component';
import { SubCostCategoryService } from 'app/entities/sub-cost-category/sub-cost-category.service';
import { SubCostCategory } from 'app/shared/model/sub-cost-category.model';

describe('Component Tests', () => {
    describe('SubCostCategory Management Component', () => {
        let comp: SubCostCategoryComponent;
        let fixture: ComponentFixture<SubCostCategoryComponent>;
        let service: SubCostCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [SubCostCategoryComponent],
                providers: []
            })
                .overrideTemplate(SubCostCategoryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SubCostCategoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubCostCategoryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SubCostCategory(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.subCostCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
