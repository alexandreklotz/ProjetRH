import { Component, OnInit } from '@angular/core';
import {TestService} from "../services/test.service";
import {Observable} from "rxjs";
import {Test} from "../models/test.model";

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent implements OnInit {

  testList$!: Observable<Test[]>

  constructor(private testService: TestService) { }

  ngOnInit(): void {
    this.testList$ = this.testService.getAllTest();
  }

}
