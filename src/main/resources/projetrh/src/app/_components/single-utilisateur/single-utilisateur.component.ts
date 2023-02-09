import { Component, OnInit } from '@angular/core';
import {Utilisateur} from "../../_models/utilisateur.model";
import {UtilisateurService} from "../../_services/_restricted/utilisateur.service";
import {ActivatedRoute} from "@angular/router";
import {v4 as uuid} from 'uuid';
import {Observable} from "rxjs";
import * as Util from "util";
import {RolesService} from "../../_services/_restricted/roles.service";
import {Roles} from "../../_models/roles.model";

@Component({
  selector: 'app-single-utilisateur',
  templateUrl: './single-utilisateur.component.html',
  styleUrls: ['./single-utilisateur.component.scss']
})
export class SingleUtilisateurComponent implements OnInit {

  utilisateur$!: Utilisateur
  rolesList$!: Roles[]

  constructor(private utilisateurService: UtilisateurService,
              private rolesService: RolesService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.retrieveAllRoles()

    const utilisateurId = this.route.snapshot.params['id'];
    this.utilisateurService.getUtilisateurById(utilisateurId).subscribe(data => {
      this.utilisateur$ = data
    })
  }

  async retrieveAllRoles(): Promise<void> {
    this.rolesList$ = await this.rolesService.getAllRoles()
  }

  onUpdate(): void{
    this.utilisateurService.updateUtilisateur(this.utilisateur$).subscribe(data => {
      console.log(this.utilisateur$)
      console.log("Update de l'utilisateur")
    })
  }

}
