import { Component, OnInit } from '@angular/core';
import {QuestionService} from "../services/question.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  constructor(private questionService: QuestionService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
