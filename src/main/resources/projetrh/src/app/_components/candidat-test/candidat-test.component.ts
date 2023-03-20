import { Component, OnInit } from '@angular/core';
import {TestService} from "../../_services/_restricted/test.service";
import {ActivatedRoute} from "@angular/router";
import {Test} from "../../_models/test.model";
import {Observable} from "rxjs";

@Component({
  selector: 'app-candidat-test',
  templateUrl: './candidat-test.component.html',
  styleUrls: ['./candidat-test.component.scss']
})
export class CandidatTestComponent implements OnInit {

  test$!: Test
  testTitre!: string

  constructor(private testService: TestService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {

    const testId = this.route.snapshot.params['id'];

    this.testService.getTestById(testId).subscribe(data => {
      this.test$ = data
    })
  }

}
