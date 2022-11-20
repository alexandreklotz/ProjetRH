import { Component, OnInit } from '@angular/core';
import {UtilisateurService} from "../services/utilisateur.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.scss']
})
export class UtilisateurComponent implements OnInit {

  constructor(private utilisateurService: UtilisateurService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
