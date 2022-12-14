import { Component, OnInit } from '@angular/core';
import {TokenService} from "../_services/token.service";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.tokenService.clearToken();
  }

}
