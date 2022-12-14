import {Component, Input, OnInit} from '@angular/core';
import {QuestionService} from "../_services/question.service";
import {Router} from "@angular/router";
import {Question} from "../_models/question.model";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  @Input() question!: Question;

  constructor(private questionService: QuestionService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
