import {Component, Input, OnInit} from '@angular/core';
import {Dashboard} from "../../_models/dashboard.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  @Input() homePage!: Dashboard;

  constructor() { }

  ngOnInit(): void {
  }

}
