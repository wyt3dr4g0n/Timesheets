import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SubCostCategory } from 'app/shared/model/sub-cost-category.model';
import { SubCostCategoryService } from './sub-cost-category.service';
import { SubCostCategoryComponent } from './sub-cost-category.component';
import { SubCostCategoryDetailComponent } from './sub-cost-category-detail.component';
import { SubCostCategoryUpdateComponent } from './sub-cost-category-update.component';
import { SubCostCategoryDeletePopupComponent } from './sub-cost-category-delete-dialog.component';
import { ISubCostCategory } from 'app/shared/model/sub-cost-category.model';

@Injectable({ providedIn: 'root' })
export class SubCostCategoryResolve implements Resolve<ISubCostCategory> {
    constructor(private service: SubCostCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SubCostCategory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SubCostCategory>) => response.ok),
                map((subCostCategory: HttpResponse<SubCostCategory>) => subCostCategory.body)
            );
        }
        return of(new SubCostCategory());
    }
}

export const subCostCategoryRoute: Routes = [
    {
        path: 'sub-cost-category',
        component: SubCostCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubCostCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-cost-category/:id/view',
        component: SubCostCategoryDetailComponent,
        resolve: {
            subCostCategory: SubCostCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubCostCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-cost-category/new',
        component: SubCostCategoryUpdateComponent,
        resolve: {
            subCostCategory: SubCostCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubCostCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sub-cost-category/:id/edit',
        component: SubCostCategoryUpdateComponent,
        resolve: {
            subCostCategory: SubCostCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubCostCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subCostCategoryPopupRoute: Routes = [
    {
        path: 'sub-cost-category/:id/delete',
        component: SubCostCategoryDeletePopupComponent,
        resolve: {
            subCostCategory: SubCostCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubCostCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
