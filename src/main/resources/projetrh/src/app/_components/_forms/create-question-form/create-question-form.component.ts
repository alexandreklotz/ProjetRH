import { Component, OnInit } from '@angular/core';
import {QcmService} from "../../../_services/_restricted/qcm.service";
import {Qcm} from "../../../_models/qcm.model";
import {QuestionService} from "../../../_services/_restricted/question.service";

@Component({
  selector: 'app-create-question-form',
  templateUrl: './create-question-form.component.html',
  styleUrls: ['./create-question-form.component.scss']
})
export class CreateQuestionFormComponent implements OnInit {

  qcmList$!: Qcm[];

  constructor(private qcmService: QcmService,
              private questionService: QuestionService) {
  }

  async ngOnInit(): Promise<void> {
    this.qcmList$ = await this.qcmService.getAllQcm()
  }


}
