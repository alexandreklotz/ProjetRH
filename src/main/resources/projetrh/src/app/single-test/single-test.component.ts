import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TestService} from "../services/test.service";

@Component({
  selector: 'app-single-test',
  templateUrl: './single-test.component.html',
  styleUrls: ['./single-test.component.scss']
})
export class SingleTestComponent implements OnInit {

  constructor(private testService: TestService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

}
