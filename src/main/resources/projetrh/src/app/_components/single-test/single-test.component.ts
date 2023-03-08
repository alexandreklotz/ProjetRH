import { Component, OnInit } from '@angular/core';
import {TestService} from "../../_services/_restricted/test.service";
import {ActivatedRoute} from "@angular/router";
import {Test} from "../../_models/test.model";
import {FormControl} from "@angular/forms";
import {Utilisateur} from "../../_models/utilisateur.model";
import {UtilisateurService} from "../../_services/_restricted/utilisateur.service";
import {Question} from "../../_models/question.model";
import {Reponse} from "../../_models/reponse.model";

@Component({
  selector: 'app-single-test',
  templateUrl: './single-test.component.html',
  styleUrls: ['./single-test.component.scss']
})
export class SingleTestComponent implements OnInit {

  test$!: Test
  canBeEdited!: boolean
  candidats$!: Utilisateur[]

  testQuestions$!: Array<Question>
  testReponses$!: Array<Reponse>

  constructor(private testService: TestService,
              private utilisateurService: UtilisateurService,
              private route: ActivatedRoute) { }


  ngOnInit(): void {

    this.retrieveCandidats()

    const testId = this.route.snapshot.params['id'];
    this.testService.getTestById(testId).subscribe(data => {
      this.test$ = data

      if(this.test$.alreadySubmitted){
        this.canBeEdited = true
      } else {
        this.canBeEdited = false
      }

    })
  }

  async retrieveCandidats(): Promise<void> {
    this.candidats$ = await this.utilisateurService.getAllCandidats()
  }

  onUpdate(){

  }

  deleteTest(testId: any){
    this.testService.deleteTest(testId)
  }

}
