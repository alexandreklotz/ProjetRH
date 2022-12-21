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

  utilisateurList$!: Observable<Utilisateur[]>

  constructor(private utilisateurService: UtilisateurService) { }

  ngOnInit(): void {
    this.utilisateurList$ = this.utilisateurService.getAllUtilisateur()
  }

}
