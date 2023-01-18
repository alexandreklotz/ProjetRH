import { Component, OnInit } from '@angular/core';
import {QcmService} from "../../_services/_restricted/qcm.service";
import {Qcm} from "../../_models/qcm.model";
import {Observable} from "rxjs";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-single-qcm',
  templateUrl: './single-qcm.component.html',
  styleUrls: ['./single-qcm.component.scss']
})
export class SingleQcmComponent implements OnInit {

  qcm!: Observable<Qcm>;

  constructor(private qcmService: QcmService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    let id = this.route.snapshot.paramMap.get('id')
    if (id){
      this.qcm = this.qcmService.getQcmById(id)
    }
  }

}
