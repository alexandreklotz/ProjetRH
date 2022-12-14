import {Component, Input, OnInit} from '@angular/core';
import {ReponseService} from "../_services/reponse.service";
import {Router} from "@angular/router";
import {Reponse} from "../_models/reponse.model";

@Component({
  selector: 'app-reponse',
  templateUrl: './reponse.component.html',
  styleUrls: ['./reponse.component.scss']
})
export class ReponseComponent implements OnInit {

  @Input() reponse!: Reponse;

  constructor(private reponseService: ReponseService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
