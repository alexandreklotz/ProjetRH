import { Component, OnInit } from '@angular/core';
import {Question} from "../../../_models/question.model";
import {QuestionService} from "../../../_services/_restricted/question.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Reponse} from "../../../_models/reponse.model";
import {HttpHeaders} from "@angular/common/http";
import {QcmService} from "../../../_services/_restricted/qcm.service";

@Component({
  selector: 'app-create-qcm-form',
  templateUrl: './create-qcm-form.component.html',
  styleUrls: ['./create-qcm-form.component.scss']
})
export class CreateQcmFormComponent implements OnInit {

  questionList$!: Question[];
  newQuestion$!: Question

  questionTemps$!: Question[]

  questionsForm!: FormArray
  qcmForm!: FormGroup

  constructor(private questionService: QuestionService,
              private qcmService: QcmService,
              private fb: FormBuilder) { }

  ngOnInit(): void {

    this.getUnassignedQuestions()

    this.qcmForm = new FormGroup({
      titre: new FormControl(''),
      questions: new FormArray([
        this.initQuestion()
      ])
    });
  }

  get formQuestions(){
    return this.qcmForm.get('questions') as FormArray
  }

  initQuestion(){
    return new FormGroup({
      id: new FormControl('')
    })
  }

  addQuestion(){
    const control = <FormArray>this.qcmForm.controls['questions']
    control.push(this.initQuestion())
  }

  async getUnassignedQuestions(): Promise<void> {
    this.questionList$ = await this.questionService.getUnassignedQuestion()
  }


  removeQuestion(index: number){
    this.questionsForm.removeAt(index);
  }

  onSubmit(){

    const formValue = JSON.stringify(this.qcmForm.value)
    console.log("formulaire envoyé : " + formValue)

    /*const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.qcmService.createQcm()*/
  }

}
