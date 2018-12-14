import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPayCode } from 'app/shared/model/pay-code.model';
import { PayCodeService } from './pay-code.service';

@Component({
    selector: 'jhi-pay-code-update',
    templateUrl: './pay-code-update.component.html'
})
export class PayCodeUpdateComponent implements OnInit {
    payCode: IPayCode;
    isSaving: boolean;

    constructor(private payCodeService: PayCodeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ payCode }) => {
            this.payCode = payCode;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.payCode.id !== undefined) {
            this.subscribeToSaveResponse(this.payCodeService.update(this.payCode));
        } else {
            this.subscribeToSaveResponse(this.payCodeService.create(this.payCode));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPayCode>>) {
        result.subscribe((res: HttpResponse<IPayCode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
