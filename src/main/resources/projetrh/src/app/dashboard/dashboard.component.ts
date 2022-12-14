import {Component, Input, OnInit} from '@angular/core';
import {Homepage} from "../_models/homepage.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  @Input() homePage!: Homepage;

  constructor() { }

  ngOnInit(): void {
  }

}
