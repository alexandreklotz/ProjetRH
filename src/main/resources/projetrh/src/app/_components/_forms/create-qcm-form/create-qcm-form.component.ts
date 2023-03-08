import { Component, OnInit } from '@angular/core';
import {Question} from "../../../_models/question.model";
import {QuestionService} from "../../../_services/_restricted/question.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Reponse} from "../../../_models/reponse.model";
import {HttpHeaders} from "@angular/common/http";
import {QcmService} from "../../../_services/_restricted/qcm.service";
import {QuestionDto} from "../../../_models/_dto/question-dto";
import {QcmInterface} from "../../../_interfaces/_json/qcm-interface";

@Component({
  selector: 'app-create-qcm-form',
  templateUrl: './create-qcm-form.component.html',
  styleUrls: ['./create-qcm-form.component.scss']
})
export class CreateQcmFormComponent implements OnInit {

  questionList$!: Question[];
  qcmTempQuestions$!: String[]

  qcmQuestions$!: Array<QuestionDto>

  json!: QcmInterface

  qcmTitre!: string
  questionId!: string


  constructor(private questionService: QuestionService,
              private qcmService: QcmService) { }


  ngOnInit(): void {
    this.getUnassignedQuestions()

    this.qcmQuestions$ = new Array<QuestionDto>
  }


  addQuestion(id: string){

    let questionDto$ = new QuestionDto()
    questionDto$.id = this.questionId
    this.qcmQuestions$.push(questionDto$)

    //this.qcmTempQuestions$.push()
  }

  getQuestionsTitre(){

  }

  async getUnassignedQuestions(): Promise<void> {
    this.questionList$ = await this.questionService.getUnassignedQuestion()
  }


  removeQuestion(index: number){
    this.qcmQuestions$.splice(index);
  }

  onSubmit(){

    this.json = {
      titre: this.qcmTitre,
      //questions: this.qcmQuestions$
      ...(this.qcmQuestions$ ? { questions: this.qcmQuestions$ } : {}),
    }


    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    console.log("JSON avant stringify : " + this.json)
    const qcmJson = JSON.stringify(this.json)
    console.log("JSON final : " + qcmJson)


    this.qcmService.createQcm(qcmJson, httpOptions).subscribe((res) => {
      console.log(res)
    },
      (err) => {
      console.log(err)
      })
  }

}
