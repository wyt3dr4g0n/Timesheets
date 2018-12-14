import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPayCode } from 'app/shared/model/pay-code.model';
import { PayCodeService } from './pay-code.service';

@Component({
    selector: 'jhi-pay-code-delete-dialog',
    templateUrl: './pay-code-delete-dialog.component.html'
})
export class PayCodeDeleteDialogComponent {
    payCode: IPayCode;

    constructor(private payCodeService: PayCodeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payCodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'payCodeListModification',
                content: 'Deleted an payCode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pay-code-delete-popup',
    template: ''
})
export class PayCodeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ payCode }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PayCodeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.payCode = payCode;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
