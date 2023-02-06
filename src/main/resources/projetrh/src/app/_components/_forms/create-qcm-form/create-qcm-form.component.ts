import { Component, OnInit } from '@angular/core';
import {Question} from "../../../_models/question.model";
import {QuestionService} from "../../../_services/_restricted/question.service";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-create-qcm-form',
  templateUrl: './create-qcm-form.component.html',
  styleUrls: ['./create-qcm-form.component.scss']
})
export class CreateQcmFormComponent implements OnInit {

  questionList$!: Question[];

  questionsForm!: FormArray
  form!: FormGroup

  constructor(private questionService: QuestionService) { }

  async ngOnInit(): Promise<void> {
    this.questionList$ = await this.questionService.getUnassignedQuestion()

    this.form = new FormGroup({
      "titre": new FormControl(''),
      "questions": new FormArray([])
    });
    this.questionsForm = this.form.get('questions') as FormArray
  }

  addQuestion(){
    this.questionsForm.push(new FormControl(''));
  }

  removeQuestion(index: number){
    this.questionsForm.removeAt(index);
  }

  onSubmit(){
    console.log(this.form)
  }

}
