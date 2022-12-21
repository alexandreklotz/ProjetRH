import {Component, Input, OnInit} from '@angular/core';
import {Utilisateur} from "../../_models/utilisateur.model";

@Component({
  selector: 'app-utilisateur',
  templateUrl: './utilisateur.component.html',
  styleUrls: ['./utilisateur.component.scss']
})
export class UtilisateurComponent implements OnInit {

  @Input() utilisateur!: Utilisateur

  constructor() { }

  ngOnInit(): void {
  }

}
