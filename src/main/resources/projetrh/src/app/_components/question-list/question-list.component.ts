import { Component, OnInit } from '@angular/core';
import {QuestionService} from "../../_services/_restricted/question.service";
import {Router} from "@angular/router";
import {Question} from "../../_models/question.model";
import {Qcm} from "../../_models/qcm.model";

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.scss']
})
export class QuestionListComponent implements OnInit {

  headers = ["Intitulé", "Temps imparti", "Points", "QCM"]
  questionList$!: Question[]

  constructor(private questionService: QuestionService,
              private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.questionList$ = await this.questionService.getQcmQuestions()
  }

  onQuestionClick(id: string){
    this.router.navigateByUrl('question/' + id)
  }

}
