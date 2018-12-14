import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubCostCategory } from 'app/shared/model/sub-cost-category.model';
import { Principal } from 'app/core';
import { SubCostCategoryService } from './sub-cost-category.service';

@Component({
    selector: 'jhi-sub-cost-category',
    templateUrl: './sub-cost-category.component.html'
})
export class SubCostCategoryComponent implements OnInit, OnDestroy {
    subCostCategories: ISubCostCategory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private subCostCategoryService: SubCostCategoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.subCostCategoryService.query().subscribe(
            (res: HttpResponse<ISubCostCategory[]>) => {
                this.subCostCategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSubCostCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISubCostCategory) {
        return item.id;
    }

    registerChangeInSubCostCategories() {
        this.eventSubscriber = this.eventManager.subscribe('subCostCategoryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
