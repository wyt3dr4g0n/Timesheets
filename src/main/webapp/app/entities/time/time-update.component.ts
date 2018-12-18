import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITime } from 'app/shared/model/time.model';
import { TimeService } from './time.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task';
import { ISubCostCategory } from 'app/shared/model/sub-cost-category.model';
import { SubCostCategoryService } from 'app/entities/sub-cost-category';
import { IPayCode } from 'app/shared/model/pay-code.model';
import { PayCodeService } from 'app/entities/pay-code';
import { Principal, Account } from 'app/core';

@Component({
    selector: 'jhi-time-update',
    templateUrl: './time-update.component.html'
})
export class TimeUpdateComponent implements OnInit {
    time: ITime;
    isSaving: boolean;
    account: Account;
    projects: IProject[];

    tasks: ITask[];

    subcostcategories: ISubCostCategory[];

    paycodes: IPayCode[];
    dateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private timeService: TimeService,
        private projectService: ProjectService,
        private taskService: TaskService,
        private subCostCategoryService: SubCostCategoryService,
        private payCodeService: PayCodeService,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ time }) => {
            this.time = time;
        });
        this.principal.identity().then(account => {
            this.time.createdBy = account.login;
        });
        this.projectService.query().subscribe(
            (res: HttpResponse<IProject[]>) => {
                this.projects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.taskService.query().subscribe(
            (res: HttpResponse<ITask[]>) => {
                this.tasks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.subCostCategoryService.query().subscribe(
            (res: HttpResponse<ISubCostCategory[]>) => {
                this.subcostcategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.payCodeService.query().subscribe(
            (res: HttpResponse<IPayCode[]>) => {
                this.paycodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.time.id !== undefined) {
            this.subscribeToSaveResponse(this.timeService.update(this.time));
        } else {
            this.subscribeToSaveResponse(this.timeService.create(this.time));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITime>>) {
        result.subscribe((res: HttpResponse<ITime>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProjectById(index: number, item: IProject) {
        return item.id;
    }

    trackTaskById(index: number, item: ITask) {
        return item.id;
    }

    trackSubCostCategoryById(index: number, item: ISubCostCategory) {
        return item.id;
    }

    trackPayCodeById(index: number, item: IPayCode) {
        return item.id;
    }
}
