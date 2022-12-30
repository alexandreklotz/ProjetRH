import { Component, OnInit } from '@angular/core';
import {TestService} from "../../_services/_restricted/test.service";
import {Observable} from "rxjs";
import {Test} from "../../_models/test.model";

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent implements OnInit {

  testList$!: Test[]
  testHeaders = ["Nom", "Score", "Candidat", "Statut"]

  constructor(private testService: TestService) { }

  async ngOnInit(): Promise<void> {
    this.testList$ = await this.testService.getAllTest()
  }

}
