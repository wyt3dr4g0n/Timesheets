import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubCostCategory } from 'app/shared/model/sub-cost-category.model';
import { SubCostCategoryService } from './sub-cost-category.service';

@Component({
    selector: 'jhi-sub-cost-category-delete-dialog',
    templateUrl: './sub-cost-category-delete-dialog.component.html'
})
export class SubCostCategoryDeleteDialogComponent {
    subCostCategory: ISubCostCategory;

    constructor(
        private subCostCategoryService: SubCostCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.subCostCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'subCostCategoryListModification',
                content: 'Deleted an subCostCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sub-cost-category-delete-popup',
    template: ''
})
export class SubCostCategoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ subCostCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SubCostCategoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.subCostCategory = subCostCategory;
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
