import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITime } from 'app/shared/model/time.model';

type EntityResponseType = HttpResponse<ITime>;
type EntityArrayResponseType = HttpResponse<ITime[]>;

@Injectable({ providedIn: 'root' })
export class TimeService {
    public resourceUrl = SERVER_API_URL + 'api/times';

    constructor(private http: HttpClient) {}

    create(time: ITime): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(time);
        return this.http
            .post<ITime>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(time: ITime): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(time);
        return this.http
            .put<ITime>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITime>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITime[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(time: ITime): ITime {
        const copy: ITime = Object.assign({}, time, {
            date: time.date != null && time.date.isValid() ? time.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((time: ITime) => {
                time.date = time.date != null ? moment(time.date) : null;
            });
        }
        return res;
    }
}
