import { Component, OnInit } from '@angular/core';
import {QcmService} from "../../../_services/_restricted/qcm.service";
import {Qcm} from "../../../_models/qcm.model";
import {QuestionService} from "../../../_services/_restricted/question.service";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {QuestionInterface} from "../../../_interfaces/_json/question-interface";

@Component({
  selector: 'app-create-question-form',
  templateUrl: './create-question-form.component.html',
  styleUrls: ['./create-question-form.component.scss']
})
export class CreateQuestionFormComponent implements OnInit {

  qcmList$!: Qcm[];

  texte!: string
  tempsReponse!: number
  points!: number
  qcmId!: string

  questionForm!: FormGroup

  constructor(private qcmService: QcmService,
              private questionService: QuestionService,
              private fb: FormBuilder) {
  }

  async ngOnInit(): Promise<void> {
    this.qcmList$ = await this.qcmService.getAllQcm()



  }

  onSubmit(){

  }


}
