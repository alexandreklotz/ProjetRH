import { Component, OnInit } from '@angular/core';
import {TestService} from "../../_services/_restricted/test.service";
import {ActivatedRoute} from "@angular/router";
import {Test} from "../../_models/test.model";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-single-test',
  templateUrl: './single-test.component.html',
  styleUrls: ['./single-test.component.scss']
})
export class SingleTestComponent implements OnInit {

  test$!: Test

  constructor(private testService: TestService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {

    const testId = this.route.snapshot.params['id'];
    this.testService.getTestById(testId).subscribe(data => {
      this.test$ = data
    })

  }

  onUpdate(){

  }

}
