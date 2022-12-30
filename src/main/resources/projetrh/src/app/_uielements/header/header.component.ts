import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {TokenService} from "../../_services/token.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  admUser!: boolean
  recrUser!: boolean
  candUser!: boolean

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {
    this.headerDisplay()
  }

  logout(): void {
    this.tokenService.clearToken()
  }

  headerDisplay(): void {
    const token = this.tokenService.getToken()
    const userRole = this.tokenService.getRoleFromToken(<string>token)

    if(userRole.includes('ADMIN')){
      this.admUser = true;
    } else {
      this.admUser = false;
    }

    if(userRole.includes('RECRUTEUR')){
      this.recrUser = true;
    } else {
      this.recrUser = false;
    }

    if(userRole.includes('CANDIDAT')){
      this.candUser = true;
    } else {
      this.candUser = false;
    }

  }


}
