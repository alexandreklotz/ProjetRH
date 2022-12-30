import { Component, OnInit } from '@angular/core';
import {Utilisateur} from "../../_models/utilisateur.model";
import {UtilisateurService} from "../../_services/_restricted/utilisateur.service";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import * as Util from "util";

@Component({
  selector: 'app-single-utilisateur',
  templateUrl: './single-utilisateur.component.html',
  styleUrls: ['./single-utilisateur.component.scss']
})
export class SingleUtilisateurComponent implements OnInit {

  utilisateur!: Observable<Utilisateur>

  constructor(private utilisateurService: UtilisateurService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    const utilisateurId = +this.route.snapshot.params['id'];
    this.utilisateur = this.utilisateurService.getUtilisateurById(String(utilisateurId))
  }

}
