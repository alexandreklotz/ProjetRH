import { Component, OnInit } from '@angular/core';
import {QuestionService} from "../../_services/_restricted/question.service";
import {Question} from "../../_models/question.model";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";

@Component({
  selector: 'app-single-question',
  templateUrl: './single-question.component.html',
  styleUrls: ['./single-question.component.scss']
})
export class SingleQuestionComponent implements OnInit {

  question!: Observable<Question>

  constructor(private questionService: QuestionService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    let id = this.route.snapshot.paramMap.get('id')
    if(id){
      this.question = this.questionService.getQuestionById(id)
    }
  }

}
