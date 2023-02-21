import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Utilisateur} from "../../_models/utilisateur.model";
import {UtilisateurService} from "../../_services/_restricted/utilisateur.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-utilisateur-list',
  templateUrl: './utilisateur-list.component.html',
  styleUrls: ['./utilisateur-list.component.scss']
})
export class UtilisateurListComponent implements OnInit {

  title = "Liste des utilisateurs"
  headers = ["Login", "Role", "Description", "Adresse mail", "Score"];

  utilisateurList$!: Utilisateur[]

  constructor(private utilisateurService: UtilisateurService,
              private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.utilisateurList$ = await this.utilisateurService.getAllUtilisateur()
  }

  onUtilisateurClick(id: string){
    this.router.navigateByUrl('utilisateur/' + id)
  }

  deleteUser(userId: any){
    this.utilisateurService.deleteUtilisateur(userId)
  }

}
