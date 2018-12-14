import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Time } from 'app/shared/model/time.model';
import { TimeService } from './time.service';
import { TimeComponent } from './time.component';
import { TimeDetailComponent } from './time-detail.component';
import { TimeUpdateComponent } from './time-update.component';
import { TimeDeletePopupComponent } from './time-delete-dialog.component';
import { ITime } from 'app/shared/model/time.model';

@Injectable({ providedIn: 'root' })
export class TimeResolve implements Resolve<ITime> {
    constructor(private service: TimeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Time> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Time>) => response.ok),
                map((time: HttpResponse<Time>) => time.body)
            );
        }
        return of(new Time());
    }
}

export const timeRoute: Routes = [
    {
        path: 'time',
        component: TimeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Times'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time/:id/view',
        component: TimeDetailComponent,
        resolve: {
            time: TimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Times'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time/new',
        component: TimeUpdateComponent,
        resolve: {
            time: TimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Times'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'time/:id/edit',
        component: TimeUpdateComponent,
        resolve: {
            time: TimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Times'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timePopupRoute: Routes = [
    {
        path: 'time/:id/delete',
        component: TimeDeletePopupComponent,
        resolve: {
            time: TimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Times'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
