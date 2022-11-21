import {Component, Input, OnInit} from '@angular/core';
import {Homepage} from "../models/homepage.model";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  @Input() homePage!: Homepage;

  constructor() { }

  ngOnInit(): void {
  }

}
