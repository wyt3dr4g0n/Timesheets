import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITime } from 'app/shared/model/time.model';
import { TimeService } from './time.service';

@Component({
    selector: 'jhi-time-delete-dialog',
    templateUrl: './time-delete-dialog.component.html'
})
export class TimeDeleteDialogComponent {
    time: ITime;

    constructor(private timeService: TimeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'timeListModification',
                content: 'Deleted an time'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-delete-popup',
    template: ''
})
export class TimeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ time }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TimeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.time = time;
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
