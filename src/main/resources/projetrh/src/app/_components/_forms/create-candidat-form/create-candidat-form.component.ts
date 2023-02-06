import { Component, OnInit } from '@angular/core';
import {Utilisateur} from "../../../_models/utilisateur.model";
import {CandidatInterface} from "../../../_interfaces/_json/candidat-interface";
import {FormControl, FormGroup} from "@angular/forms";
import {UtilisateurService} from "../../../_services/_restricted/utilisateur.service";
import {HttpHeaders} from "@angular/common/http";


@Component({
  selector: 'app-create-candidat-form',
  templateUrl: './create-candidat-form.component.html',
  styleUrls: ['./create-candidat-form.component.scss']
})
export class CreateCandidatFormComponent implements OnInit {

  firstName!: string
  lastName!: string
  userLogin!: string
  mailAddress!: string
  userPassword!: string
  userDescription!: string

  form!: FormGroup

  json!: CandidatInterface

  constructor(private utilisateurService: UtilisateurService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      "firstName" : new FormControl(''),
      "lastName" : new FormControl(''),
      "userLogin" : new FormControl(''),
      "mailAddress" : new FormControl(''),
      "userPassword" : new FormControl(''),
      "userDescription" : new FormControl('')
    });

    this.form.valueChanges.subscribe(formData => {
      this.json = {
        firstName: formData.firstName,
        lastName: formData.lastName,
        userLogin: formData.userLogin,
        mailAddress: formData.mailAddress,
        userPassword: formData.userPassword,
        userDescription: formData.userDescription
      };
    });
  }

  onSubmit() {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    console.log(this.json)

    this.utilisateurService.createCandidat(this.json, httpOptions).subscribe(
      (res) => {
        console.log(res)
      },
      (error) => {
        console.log(error)
      }
    )
  }

}
