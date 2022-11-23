import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Roles} from "../models/roles.model";
import {RolesService} from "../services/roles.service";

@Component({
  selector: 'app-roles-list',
  templateUrl: './roles-list.component.html',
  styleUrls: ['./roles-list.component.scss']
})
export class RolesListComponent implements OnInit {

  rolesList$!: Observable<Roles>

  constructor(private rolesService: RolesService) { }

  ngOnInit(): void {
  }

}
