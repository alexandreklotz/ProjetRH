import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QcmService} from "../services/qcm.service";

@Component({
  selector: 'app-single-qcm',
  templateUrl: './single-qcm.component.html',
  styleUrls: ['./single-qcm.component.scss']
})
export class SingleQcmComponent implements OnInit {

  constructor(private qcmService: QcmService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

}
