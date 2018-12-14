import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITime } from 'app/shared/model/time.model';
import { Principal } from 'app/core';
import { TimeService } from './time.service';

@Component({
    selector: 'jhi-time',
    templateUrl: './time.component.html'
})
export class TimeComponent implements OnInit, OnDestroy {
    times: ITime[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private timeService: TimeService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.timeService.query().subscribe(
            (res: HttpResponse<ITime[]>) => {
                this.times = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTimes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITime) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInTimes() {
        this.eventSubscriber = this.eventManager.subscribe('timeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
