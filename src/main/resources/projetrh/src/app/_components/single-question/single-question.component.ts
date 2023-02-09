import { Component, OnInit } from '@angular/core';
import {QuestionService} from "../../_services/_restricted/question.service";
import {Question} from "../../_models/question.model";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {Reponse} from "../../_models/reponse.model";
import {ReponseService} from "../../_services/_restricted/reponse.service";

@Component({
  selector: 'app-single-question',
  templateUrl: './single-question.component.html',
  styleUrls: ['./single-question.component.scss']
})
export class SingleQuestionComponent implements OnInit {

  question$!: Question
  newReponse$!: Reponse
  questionReponses$!: Reponse[]
  headers = ["Texte", "Réponse correcte"]

  constructor(private questionService: QuestionService,
              private reponseService: ReponseService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    const questionId = this.route.snapshot.params['id']
    this.questionService.getQuestionById(questionId).subscribe(data => {
      this.question$ = data;
    })

    this.reponseService.getReponseByQuestion(questionId).subscribe(data => {
      this.questionReponses$ = data;
    })

  }

  addReponse(reponseId: any): void {

  }


  deleteReponse(reponseId: any): void {
    this.questionReponses$.filter(reponse => reponse.id !== reponseId)
  }

}
