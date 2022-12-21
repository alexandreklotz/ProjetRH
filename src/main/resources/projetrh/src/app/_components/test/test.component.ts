import {Component, Input, OnInit} from '@angular/core';
import {Test} from "../../_models/test.model";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

  @Input() test!: Test;

  constructor() { }

  ngOnInit(): void {
  }

}
