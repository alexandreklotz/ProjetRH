import { Component, OnInit } from '@angular/core';
import {TokenService} from "../../_services/token.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.tokenService.clearToken()
  }

}
