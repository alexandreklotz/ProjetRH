import { Component, OnInit } from '@angular/core';
import {Question} from "../../../_models/question.model";
import {QuestionService} from "../../../_services/_restricted/question.service";

@Component({
  selector: 'app-create-qcm-form',
  templateUrl: './create-qcm-form.component.html',
  styleUrls: ['./create-qcm-form.component.scss']
})
export class CreateQcmFormComponent implements OnInit {

  questionList$!: Question[];
  selectedQuestion!: Question;
  selectedQuestions!: Question[];

  constructor(private questionService: QuestionService) { }

  async ngOnInit(): Promise<void> {
    this.questionList$ = await this.questionService.getUnassignedQuestion()
  }

  addSelectedObject(){
    this.selectedQuestions.push(this.selectedQuestion)
  }

}
