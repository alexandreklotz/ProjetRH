import { Component, OnInit } from '@angular/core';
import {TestService} from "../services/test.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

  constructor(private testService: TestService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
