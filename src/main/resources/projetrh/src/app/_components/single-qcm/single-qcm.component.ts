import { Component, OnInit } from '@angular/core';
import {QcmService} from "../../_services/_restricted/qcm.service";
import {Qcm} from "../../_models/qcm.model";
import {Observable} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {Question} from "../../_models/question.model";
import {Test} from "../../_models/test.model";
import {QuestionService} from "../../_services/_restricted/question.service";

@Component({
  selector: 'app-single-qcm',
  templateUrl: './single-qcm.component.html',
  styleUrls: ['./single-qcm.component.scss']
})
export class SingleQcmComponent implements OnInit {

  qcm$!: Qcm
  qcmQuestions!: Question[]
  unassignedQuestions$!: Question[]
  question$!: Question
  selectedQuestionId!: string


  constructor(private qcmService: QcmService,
              private questionService: QuestionService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.retrieveUnassignedQuestions()

    const qcmId = this.route.snapshot.params['id'];
    this.qcmService.getQcmById(qcmId).subscribe(data => {
      this.qcm$ = data
      // @ts-ignore
      this.qcmQuestions = this.qcm$.questions
    })


  }

  async retrieveUnassignedQuestions(): Promise<void> {
    this.unassignedQuestions$ = await this.questionService.getUnassignedQuestion()
  }

  goBackToList(){
    setTimeout(() => {
      this.router.navigateByUrl('/qcms', {replaceUrl: true})
    }, 1000)
  }

  onUpdate(): void {

    if(this.qcmQuestions){
      this.qcm$.questions = this.qcmQuestions
      this.qcm$.questions.forEach((question) => {
        console.log("final questions id for each : " + question.id)
      })
      console.log("qcm questions : " + this.qcm$.questions)
    } else {
      delete this.qcm$.questions
    }

    this.qcmService.updateQcm(this.qcm$).subscribe(data => {
      console.log("Update du qcm")
    })

    this.goBackToList()
  }

  addQuestion(questionId: any): void {
    this.questionService.getQuestionById(questionId).subscribe(data => {
      this.question$ = data
      this.qcmQuestions.push(this.question$)
    })
  }

  editQuestion(questionId: any): void {
    this.router.navigateByUrl("question/" + questionId)
  }

  removeQuestion(questionId: any): void {
    this.qcmQuestions = this.qcmQuestions.filter(question => question.id !== questionId);
  }

  deleteQcm(qcmId: any){
    this.qcmService.deleteQcm(qcmId)
    this.goBackToList()
  }

}
