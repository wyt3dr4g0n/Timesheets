/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimesheetsTestModule } from '../../../test.module';
import { SubCostCategoryDetailComponent } from 'app/entities/sub-cost-category/sub-cost-category-detail.component';
import { SubCostCategory } from 'app/shared/model/sub-cost-category.model';

describe('Component Tests', () => {
    describe('SubCostCategory Management Detail Component', () => {
        let comp: SubCostCategoryDetailComponent;
        let fixture: ComponentFixture<SubCostCategoryDetailComponent>;
        const route = ({ data: of({ subCostCategory: new SubCostCategory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [SubCostCategoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SubCostCategoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SubCostCategoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.subCostCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
