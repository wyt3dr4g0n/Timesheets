/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimesheetsTestModule } from '../../../test.module';
import { TimeDetailComponent } from 'app/entities/time/time-detail.component';
import { Time } from 'app/shared/model/time.model';

describe('Component Tests', () => {
    describe('Time Management Detail Component', () => {
        let comp: TimeDetailComponent;
        let fixture: ComponentFixture<TimeDetailComponent>;
        const route = ({ data: of({ time: new Time(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [TimeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.time).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
