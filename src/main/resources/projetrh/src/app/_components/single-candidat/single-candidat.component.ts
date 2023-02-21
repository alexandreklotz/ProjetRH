import { Component, OnInit } from '@angular/core';
import {UtilisateurService} from "../../_services/_restricted/utilisateur.service";
import {Utilisateur} from "../../_models/utilisateur.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-single-candidat',
  templateUrl: './single-candidat.component.html',
  styleUrls: ['./single-candidat.component.scss']
})
export class SingleCandidatComponent implements OnInit {

  candidat$!: Utilisateur

  constructor(private utilisateurService: UtilisateurService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    const candidatId = this.route.snapshot.params['id'];
    this.utilisateurService.getCandidatById(candidatId).subscribe(data => {
      this.candidat$ = data
    })

  }

  onUpdate(candidat: Utilisateur){
    this.utilisateurService.updateCandidat(this.candidat$).subscribe(data => {
      console.log(this.candidat$)
      console.log("Update du candidat")
    })

  }

  deleteCandidat(candidatId: any){
    this.utilisateurService.deleteCandidat(candidatId)
  }

}
