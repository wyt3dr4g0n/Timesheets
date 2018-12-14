import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISubCostCategory } from 'app/shared/model/sub-cost-category.model';
import { SubCostCategoryService } from './sub-cost-category.service';

@Component({
    selector: 'jhi-sub-cost-category-update',
    templateUrl: './sub-cost-category-update.component.html'
})
export class SubCostCategoryUpdateComponent implements OnInit {
    subCostCategory: ISubCostCategory;
    isSaving: boolean;

    constructor(private subCostCategoryService: SubCostCategoryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ subCostCategory }) => {
            this.subCostCategory = subCostCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.subCostCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.subCostCategoryService.update(this.subCostCategory));
        } else {
            this.subscribeToSaveResponse(this.subCostCategoryService.create(this.subCostCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISubCostCategory>>) {
        result.subscribe((res: HttpResponse<ISubCostCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
