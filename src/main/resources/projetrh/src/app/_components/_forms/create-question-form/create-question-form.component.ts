import { Component, OnInit } from '@angular/core';
import {QcmService} from "../../../_services/_restricted/qcm.service";
import {Qcm} from "../../../_models/qcm.model";
import {QuestionService} from "../../../_services/_restricted/question.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {QuestionInterface} from "../../../_interfaces/_json/question-interface";
import {Question} from "../../../_models/question.model";
import {HttpHeaders} from "@angular/common/http";
import {Reponse} from "../../../_models/reponse.model";
import {lastValueFrom, Observable, Subscription} from "rxjs";
import {ReponseDto} from "../../../_models/_dto/reponse-dto";

@Component({
  selector: 'app-create-question-form',
  templateUrl: './create-question-form.component.html',
  styleUrls: ['./create-question-form.component.scss']
})
export class CreateQuestionFormComponent implements OnInit {

  qcmList$!: Qcm[];

  questionTexte!: string
  questionTempsReponse!: number
  questionPoints!: number
  qcmId!: string

  json!: QuestionInterface


  correctAnswer!: boolean
  reponseTexte!: string
  reponses$!: Array<ReponseDto>


  constructor(private qcmService: QcmService,
              private questionService: QuestionService) {
  }

  ngOnInit(): void {

    this.getQcm()

    this.reponses$ = new Array<ReponseDto>()
  }

  async getQcm(): Promise<void> {
    this.qcmList$ = await this.qcmService.getAllQcm()
  }


  addReponse(){
    let reponseDto$ = new ReponseDto()
    reponseDto$.texte = this.reponseTexte
    reponseDto$.correctAnswer = this.correctAnswer

    this.reponses$.push(reponseDto$)
  }


  removeReponse(index: number){
    this.reponses$.splice(index)
  }


  onSubmit(){

    this.json = {
      texte: this.questionTexte,
      tempsReponse: this.questionTempsReponse,
      points: this.questionPoints,
      qcm : {
        id: this.qcmId
      },
      reponses: this.reponses$
    }


    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    const questionJson = JSON.stringify(this.json)

    this.questionService.createQuestion(questionJson, httpOptions).subscribe((res) => {
      console.log(res)
    },
      (err) => {
      console.log(err)
      })

  }


}
