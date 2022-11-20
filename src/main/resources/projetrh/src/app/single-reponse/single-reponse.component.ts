import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ReponseService} from "../services/reponse.service";

@Component({
  selector: 'app-single-reponse',
  templateUrl: './single-reponse.component.html',
  styleUrls: ['./single-reponse.component.scss']
})
export class SingleReponseComponent implements OnInit {

  constructor(private reponseService: ReponseService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

}
