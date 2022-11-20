import { Component, OnInit } from '@angular/core';
import {ReponseService} from "../services/reponse.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-reponse',
  templateUrl: './reponse.component.html',
  styleUrls: ['./reponse.component.scss']
})
export class ReponseComponent implements OnInit {

  constructor(private reponseService: ReponseService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
