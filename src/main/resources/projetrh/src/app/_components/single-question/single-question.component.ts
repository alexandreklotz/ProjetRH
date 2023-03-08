import { Component, OnInit } from '@angular/core';
import {QuestionService} from "../../_services/_restricted/question.service";
import {Question} from "../../_models/question.model";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {Reponse} from "../../_models/reponse.model";
import {ReponseService} from "../../_services/_restricted/reponse.service";
import {QcmService} from "../../_services/_restricted/qcm.service";
import {Qcm} from "../../_models/qcm.model";
import {ReponseDto} from "../../_models/_dto/reponse-dto";
import {HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-single-question',
  templateUrl: './single-question.component.html',
  styleUrls: ['./single-question.component.scss']
})
export class SingleQuestionComponent implements OnInit {

  question$!: Question
  reponseTexte!: string
  correctAnswer!: boolean
  questionReponses$!: Reponse[]

  newRepId!: string

  qcmList$!: Qcm[]
  headers = ["Texte", "Réponse correcte"]

  constructor(private questionService: QuestionService,
              private reponseService: ReponseService,
              private qcmService: QcmService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.getAllQcm()

    const questionId = this.route.snapshot.params['id']
    this.questionService.getQuestionById(questionId).subscribe(data => {
      this.question$ = data;
    })

    this.reponseService.getReponseByQuestion(questionId).subscribe(data => {
      this.questionReponses$ = data;
    })

  }

  async getAllQcm(){
    this.qcmList$ = await this.qcmService.getAllQcm()
  }

  addReponse() {
    let reponseDto$ = new ReponseDto()
    reponseDto$.texte = this.reponseTexte
    reponseDto$.correctAnswer = this.correctAnswer

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    let repDtoJson = JSON.stringify(reponseDto$)
    this.reponseService.createNewReponseInQuestion(reponseDto$, this.question$.id, httpOptions).subscribe((res) => {
      console.log(res)
      this.newRepId = res.id
    }, (err) => {
      console.log(err)
      }
    )

    /*let newReponse = this.reponseService.getReponseById(this.newRepId)
    this.questionReponses$.push(newReponse)*/
  }


  deleteReponse(reponseId: any): void {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.questionReponses$.filter(reponse => reponse.id !== reponseId)
    this.reponseService.deleteReponse(reponseId, httpOptions)
  }

  deleteQuestion(questionId: any){
    this.questionService.deleteQuestion(questionId)
  }

  onUpdate(question: Question){

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.questionService.updateQuestion(question, httpOptions).subscribe((res) => {
      console.log(res)
    }, (err) => {
      console.log(err)
    })
  }

}
