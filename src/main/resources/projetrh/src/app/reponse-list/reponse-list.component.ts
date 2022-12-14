import { Component, OnInit } from '@angular/core';
import {ReponseService} from "../_services/reponse.service";
import {Observable} from "rxjs";
import {Reponse} from "../_models/reponse.model";

@Component({
  selector: 'app-reponse-list',
  templateUrl: './reponse-list.component.html',
  styleUrls: ['./reponse-list.component.scss']
})
export class ReponseListComponent implements OnInit {

  reponseList$!:Observable<Reponse[]>

  constructor(private reponseService: ReponseService) { }

  ngOnInit(): void {
    this.reponseList$ = this.reponseService.getAllReponses();
  }

}
