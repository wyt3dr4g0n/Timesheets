import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PayCode } from 'app/shared/model/pay-code.model';
import { PayCodeService } from './pay-code.service';
import { PayCodeComponent } from './pay-code.component';
import { PayCodeDetailComponent } from './pay-code-detail.component';
import { PayCodeUpdateComponent } from './pay-code-update.component';
import { PayCodeDeletePopupComponent } from './pay-code-delete-dialog.component';
import { IPayCode } from 'app/shared/model/pay-code.model';

@Injectable({ providedIn: 'root' })
export class PayCodeResolve implements Resolve<IPayCode> {
    constructor(private service: PayCodeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PayCode> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PayCode>) => response.ok),
                map((payCode: HttpResponse<PayCode>) => payCode.body)
            );
        }
        return of(new PayCode());
    }
}

export const payCodeRoute: Routes = [
    {
        path: 'pay-code',
        component: PayCodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pay-code/:id/view',
        component: PayCodeDetailComponent,
        resolve: {
            payCode: PayCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pay-code/new',
        component: PayCodeUpdateComponent,
        resolve: {
            payCode: PayCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayCodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pay-code/:id/edit',
        component: PayCodeUpdateComponent,
        resolve: {
            payCode: PayCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayCodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payCodePopupRoute: Routes = [
    {
        path: 'pay-code/:id/delete',
        component: PayCodeDeletePopupComponent,
        resolve: {
            payCode: PayCodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
