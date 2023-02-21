import { Component, OnInit } from '@angular/core';
import {QcmService} from "../../../_services/_restricted/qcm.service";
import {Qcm} from "../../../_models/qcm.model";
import {QuestionService} from "../../../_services/_restricted/question.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {QuestionInterface} from "../../../_interfaces/_json/question-interface";
import {Question} from "../../../_models/question.model";
import {HttpHeaders} from "@angular/common/http";
import {Reponse} from "../../../_models/reponse.model";

@Component({
  selector: 'app-create-question-form',
  templateUrl: './create-question-form.component.html',
  styleUrls: ['./create-question-form.component.scss']
})
export class CreateQuestionFormComponent implements OnInit {

  qcmList$!: Qcm[];

  json!: QuestionInterface

  texte!: string
  tempsReponse!: number
  points!: number
  qcmId!: string
  correctAnswer!: boolean
  reponses$!: Reponse[]

  questionForm!: FormGroup

  constructor(private qcmService: QcmService,
              private questionService: QuestionService,
              private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.getQcm()

    //idée : créer un array de réponses et créer une réponse avec un id temporaire égal à l'index du tableau puis ensuite
    //le push dans le form. enlever l'id lors de l'envoi du json ou alors indiquer dans le model que la variable peut être null

    this.questionForm = new FormGroup({
      texte: new FormControl('', Validators.required),
      tempsReponse: new FormControl('', Validators.required),
      points: new FormControl('', Validators.required),
      qcm: new FormGroup ({
        id: new FormControl('')
      }),
      reponses: new FormArray([
        this.initReponse()
      ])
    });
  }

  async getQcm(): Promise<void> {
    this.qcmList$ = await this.qcmService.getAllQcm()
  }

  get reponsesForm(){
    return this.questionForm.get('reponses') as FormArray
  }

  initReponse(){
    return new FormGroup({
      texte: new FormControl('', Validators.required),
      correctAnswer: new FormControl(false, Validators.required)
    });
  }

  addReponse(){
    const control = <FormArray>this.questionForm.controls['reponses']
    control.push(this.initReponse())
  }

  onSubmit(){

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    const formValue = JSON.stringify(this.questionForm.value)
    console.log("formulaire envoyé : " + formValue)


    this.questionService.createQuestion(formValue, httpOptions).subscribe((res) => {
      console.log(res)
    },
      (err) => {
      console.log(err)
      })

  }


}
