import { Component, OnInit } from '@angular/core';
import {RolesService} from "../../../_services/_restricted/roles.service";
import {Roles} from "../../../_models/roles.model";
import {FormGroup} from "@angular/forms";
import {UtilisateurInterface} from "../../../_interfaces/_json/utilisateur-interface";
import {HttpHeaders} from "@angular/common/http";
import {UtilisateurService} from "../../../_services/_restricted/utilisateur.service";

@Component({
  selector: 'app-create-utilisateur-form',
  templateUrl: './create-utilisateur-form.component.html',
  styleUrls: ['./create-utilisateur-form.component.scss']
})
export class CreateUtilisateurFormComponent implements OnInit {

  rolesList$!: Roles[]

  firstName!: string
  lastName!: string
  userLogin!: string
  mailAddress!: string
  userPassword!: string
  userDescription!: string
  roleId!: string

  json!: UtilisateurInterface

  constructor(private rolesService: RolesService,
              private utilisateurService: UtilisateurService) { }

  async ngOnInit(): Promise<void> {
    this.rolesList$ = await this.rolesService.getAllRoles()
  }

  onSubmit(){

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    console.log("roleid : " + this.roleId)

    this.json = {
      firstName: this.firstName,
      lastName: this.lastName,
      userLogin: this.userLogin,
      mailAddress: this.mailAddress,
      userPassword: this.userPassword,
      userDescription: this.userDescription,
      role : {
        id: this.roleId
      }
    }

    console.log(this.json)

    this.utilisateurService.createUtilisateur(this.json, httpOptions).subscribe(
      (res) => {
        console.log(res)
      },
      (error) => {
        console.log(error)
      }
    )
  }

}
