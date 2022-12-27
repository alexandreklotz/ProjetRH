import {Component, Input, OnInit} from '@angular/core';
import {UserEndPointService} from "../../_services/userendpoint.service";
import {Utilisateur} from "../../_models/utilisateur.model";
import {Observable} from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  loggedUtilisateur!: Utilisateur

  constructor(private userEndPointService: UserEndPointService) { }

  async ngOnInit(): Promise<void> {
    this.loggedUtilisateur = await this.userEndPointService.userSelfRetrieve()
  }

}
