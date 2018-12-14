import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubCostCategory } from 'app/shared/model/sub-cost-category.model';

@Component({
    selector: 'jhi-sub-cost-category-detail',
    templateUrl: './sub-cost-category-detail.component.html'
})
export class SubCostCategoryDetailComponent implements OnInit {
    subCostCategory: ISubCostCategory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ subCostCategory }) => {
            this.subCostCategory = subCostCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
