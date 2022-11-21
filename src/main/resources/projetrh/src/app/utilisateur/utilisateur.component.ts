import {Component, Input, OnInit} from '@angular/core';
import {UtilisateurService} from "../services/utilisateur.service";
import {Router} from "@angular/router";
import {Utilisateur} from "../models/utilisateur.model";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.scss']
})
export class UtilisateurComponent implements OnInit {

  @Input() utilisateur!: Utilisateur;

  constructor(private utilisateurService: UtilisateurService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
