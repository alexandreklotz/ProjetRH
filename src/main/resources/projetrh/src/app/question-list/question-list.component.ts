import { Component, OnInit } from '@angular/core';
import {QuestionService} from "../_services/question.service";
import {Observable} from "rxjs";
import {Question} from "../_models/question.model";

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.scss']
})
export class QuestionListComponent implements OnInit {

  questionList$!: Observable<Question[]>

  constructor(private questionService: QuestionService) { }

  ngOnInit(): void {
    this.questionList$ = this.questionService.getAllQuestions();
  }

}
