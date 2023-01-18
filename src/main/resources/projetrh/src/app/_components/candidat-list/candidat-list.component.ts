import { Component, OnInit } from '@angular/core';
import {UtilisateurService} from "../../_services/_restricted/utilisateur.service";
import {Utilisateur} from "../../_models/utilisateur.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-candidat-list',
  templateUrl: './candidat-list.component.html',
  styleUrls: ['./candidat-list.component.scss']
})
export class CandidatListComponent implements OnInit {

  candidatList$!: Utilisateur[]
  headers = ["Nom", "Prénom", "Description", "Login", "Adresse mail"]

  constructor(private utilisateurService: UtilisateurService,
              private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.candidatList$ = await this.utilisateurService.getAllCandidats()
  }

  onCandidatClick(id: string){
    this.router.navigateByUrl('candidat/' + id)
  }

}
