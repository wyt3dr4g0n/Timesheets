import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPayCode } from 'app/shared/model/pay-code.model';

type EntityResponseType = HttpResponse<IPayCode>;
type EntityArrayResponseType = HttpResponse<IPayCode[]>;

@Injectable({ providedIn: 'root' })
export class PayCodeService {
    public resourceUrl = SERVER_API_URL + 'api/pay-codes';

    constructor(private http: HttpClient) {}

    create(payCode: IPayCode): Observable<EntityResponseType> {
        return this.http.post<IPayCode>(this.resourceUrl, payCode, { observe: 'response' });
    }

    update(payCode: IPayCode): Observable<EntityResponseType> {
        return this.http.put<IPayCode>(this.resourceUrl, payCode, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPayCode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPayCode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
