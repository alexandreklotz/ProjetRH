import {Component, Input, OnInit} from '@angular/core';
import {TestService} from "../_services/test.service";
import {Router} from "@angular/router";
import {Test} from "../_models/test.model";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

  @Input() test!: Test;

  constructor(private testService: TestService,
              private router: Router) { }

  ngOnInit(): void {
  }

}
