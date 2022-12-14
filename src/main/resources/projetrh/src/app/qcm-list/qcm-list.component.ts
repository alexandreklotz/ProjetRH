import {Component, Input, OnInit} from '@angular/core';
import { Qcm } from "../_models/qcm.model";
import { QcmService } from "../_services/qcm.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-qcm-list',
  templateUrl: './qcm-list.component.html',
  styleUrls: ['./qcm-list.component.scss']
})

export class QcmListComponent implements OnInit {

  qcmList$!: Observable<Qcm[]>

  constructor(private qcmService: QcmService) { }

  ngOnInit(): void {
    this.qcmList$ = this.qcmService.getAllQcm();
  }

}
