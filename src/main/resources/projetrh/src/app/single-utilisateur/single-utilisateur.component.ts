import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UtilisateurService} from "../services/utilisateur.service";

@Component({
  selector: 'app-single-utilisateur',
  templateUrl: './single-utilisateur.component.html',
  styleUrls: ['./single-utilisateur.component.scss']
})
export class SingleUtilisateurComponent implements OnInit {

  constructor(private utilisateurService: UtilisateurService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

}
