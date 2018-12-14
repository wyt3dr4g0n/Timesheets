import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPayCode } from 'app/shared/model/pay-code.model';
import { Principal } from 'app/core';
import { PayCodeService } from './pay-code.service';

@Component({
    selector: 'jhi-pay-code',
    templateUrl: './pay-code.component.html'
})
export class PayCodeComponent implements OnInit, OnDestroy {
    payCodes: IPayCode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payCodeService: PayCodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.payCodeService.query().subscribe(
            (res: HttpResponse<IPayCode[]>) => {
                this.payCodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPayCodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPayCode) {
        return item.id;
    }

    registerChangeInPayCodes() {
        this.eventSubscriber = this.eventManager.subscribe('payCodeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
