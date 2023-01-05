import { Component, OnInit } from '@angular/core';
import {Utilisateur} from "../../_models/utilisateur.model";
import {UserEndPointService} from "../../_services/userendpoint.service";

@Component({
  selector: 'app-recruteur-panel',
  templateUrl: './recruteur-panel.component.html',
  styleUrls: ['./recruteur-panel.component.scss']
})
export class RecruteurPanelComponent implements OnInit {

  loggedUtilisateur!: Utilisateur
  titre!: string

  constructor(private userEndPointService: UserEndPointService) { }

  async ngOnInit(): Promise<void> {
    this.loggedUtilisateur = await this.userEndPointService.userSelfRetrieve()
    this.titre = this.loggedUtilisateur.firstName + " " + this.loggedUtilisateur.lastName
  }

}
