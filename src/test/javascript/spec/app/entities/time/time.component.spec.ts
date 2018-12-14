/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TimesheetsTestModule } from '../../../test.module';
import { TimeComponent } from 'app/entities/time/time.component';
import { TimeService } from 'app/entities/time/time.service';
import { Time } from 'app/shared/model/time.model';

describe('Component Tests', () => {
    describe('Time Management Component', () => {
        let comp: TimeComponent;
        let fixture: ComponentFixture<TimeComponent>;
        let service: TimeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TimesheetsTestModule],
                declarations: [TimeComponent],
                providers: []
            })
                .overrideTemplate(TimeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Time(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.times[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
