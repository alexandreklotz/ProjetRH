import { Component, OnInit } from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {TestService} from "../../../_services/_restricted/test.service";
import {Qcm} from "../../../_models/qcm.model";
import {Utilisateur} from "../../../_models/utilisateur.model";
import {UtilisateurService} from "../../../_services/_restricted/utilisateur.service";
import {QcmService} from "../../../_services/_restricted/qcm.service";
import {TestInterface} from "../../../_interfaces/_json/test-interface";
import {HttpHeaders} from "@angular/common/http";


@Component({
  selector: 'app-create-test-form',
  templateUrl: './create-test-form.component.html',
  styleUrls: ['./create-test-form.component.scss']
})
export class CreateTestFormComponent implements OnInit {

  titre!: string
  qcmId!: string
  candidatId!: string

  candidatsListe$!: Utilisateur[]
  qcmListe$!: Qcm[]

  json!: TestInterface

  constructor(private testService: TestService,
              private utilisateurService: UtilisateurService,
              private qcmService: QcmService) { }


  async ngOnInit(): Promise<void> {

    this.candidatsListe$ = await this.utilisateurService.getAllCandidats()
    this.qcmListe$ = await this.qcmService.getAllQcm()

  }

  onSubmit(){

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.json = {
      titre: this.titre,
      qcmId: this.qcmId,
      utilisateur: {
        id : this.candidatId
      }
    }

    console.log(this.json)

    this.testService.createTest(this.json, httpOptions).subscribe(
      (res) => {
        console.log(res)
      },
      (error) => {
        console.log(error)
      })

  }

}
