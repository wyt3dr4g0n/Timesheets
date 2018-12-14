import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayCode } from 'app/shared/model/pay-code.model';

@Component({
    selector: 'jhi-pay-code-detail',
    templateUrl: './pay-code-detail.component.html'
})
export class PayCodeDetailComponent implements OnInit {
    payCode: IPayCode;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ payCode }) => {
            this.payCode = payCode;
        });
    }

    previousState() {
        window.history.back();
    }
}
