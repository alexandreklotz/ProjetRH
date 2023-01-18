import { Component, OnInit } from '@angular/core';
import {TestService} from "../../_services/_restricted/test.service";
import {Observable} from "rxjs";
import {Test} from "../../_models/test.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent implements OnInit {

  testList$!: Test[]
  testHeaders = ["Nom", "Score", "Candidat", "Statut"]

  constructor(private testService: TestService,
              private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.testList$ = await this.testService.getAllTest()
  }

  onTestClick(id: string){
    this.router.navigateByUrl('test/' + id)
  }

}
