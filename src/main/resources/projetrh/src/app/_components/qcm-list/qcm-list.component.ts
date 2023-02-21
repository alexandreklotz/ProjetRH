import { Component, OnInit } from '@angular/core';
import {QcmService} from "../../_services/_restricted/qcm.service";
import {Qcm} from "../../_models/qcm.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-qcm-list',
  templateUrl: './qcm-list.component.html',
  styleUrls: ['./qcm-list.component.scss']
})
export class QcmListComponent implements OnInit {

  qcmList$!: Qcm[]
  headers = ["Titre"]

  constructor(private qcmService: QcmService,
              private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.qcmList$ = await this.qcmService.getAllQcm()
  }

  onQcmClick(id: string){
    this.router.navigateByUrl("qcm/" + id)
  }

}
