import { Component, OnInit } from '@angular/core';
import {Utilisateur} from "../_models/utilisateur.model";
import {UtilisateurService} from "../_services/_restricted/utilisateur.service";
import {TokenService} from "../_services/token.service";
import {UserEndPointService} from "../_services/userendpoint.service";

@Component({
  selector: 'app-utilisateur-self-update',
  templateUrl: './utilisateur-self-update.component.html',
  styleUrls: ['./utilisateur-self-update.component.scss']
})
export class UtilisateurSelfUpdateComponent implements OnInit {

  utilisateur$!: Utilisateur

  constructor(private utilisateurService: UtilisateurService,
              private userEndPointService: UserEndPointService) { }

  async ngOnInit(): Promise<void> {
    this.utilisateur$ = await this.userEndPointService.userSelfRetrieve()
  }

  onUpdate(){}

}
