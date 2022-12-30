import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Utilisateur} from "../../_models/utilisateur.model";
import {UtilisateurService} from "../../_services/_restricted/utilisateur.service";

@Component({
  selector: 'app-utilisateur-list',
  templateUrl: './utilisateur-list.component.html',
  styleUrls: ['./utilisateur-list.component.scss']
})
export class UtilisateurListComponent implements OnInit {

  title = "Liste des utilisateurs"
  headers = ["Login", "Role", "Description", "Adresse mail", "Score"];

  utilisateurList$!: Utilisateur[]

  constructor(private utilisateurService: UtilisateurService) { }

  async ngOnInit(): Promise<void> {
    this.utilisateurList$ = await this.utilisateurService.getAllUtilisateur()
  }

}
