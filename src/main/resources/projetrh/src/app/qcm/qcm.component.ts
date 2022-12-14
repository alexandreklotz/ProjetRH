import {Component, Input, OnInit} from '@angular/core';
import { Qcm } from "../_models/qcm.model";
import { QcmService } from "../_services/qcm.service";
import {Router } from "@angular/router";

@Component({
  selector: 'app-qcm',
  templateUrl: './qcm.component.html',
  styleUrls: ['./qcm.component.scss']
})

export class QcmComponent implements OnInit {

  @Input() qcm!: Qcm;

  constructor(private qcmService: QcmService,
              private router: Router) {}

  ngOnInit(): void {
  }



}
