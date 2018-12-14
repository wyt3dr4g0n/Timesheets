import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISubCostCategory } from 'app/shared/model/sub-cost-category.model';

type EntityResponseType = HttpResponse<ISubCostCategory>;
type EntityArrayResponseType = HttpResponse<ISubCostCategory[]>;

@Injectable({ providedIn: 'root' })
export class SubCostCategoryService {
    public resourceUrl = SERVER_API_URL + 'api/sub-cost-categories';

    constructor(private http: HttpClient) {}

    create(subCostCategory: ISubCostCategory): Observable<EntityResponseType> {
        return this.http.post<ISubCostCategory>(this.resourceUrl, subCostCategory, { observe: 'response' });
    }

    update(subCostCategory: ISubCostCategory): Observable<EntityResponseType> {
        return this.http.put<ISubCostCategory>(this.resourceUrl, subCostCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISubCostCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISubCostCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
