import { Component, OnInit } from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {ICredentials} from "../_interfaces/credentials";
import {TokenService} from "../_services/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  model: any = {};
  sessionId: any = "";

  form: ICredentials = {
    userLogin: this.model.userLogin,
    userPassword: this.model.userPassword
  }

  constructor(
    private authService: AuthService,
    private tokenService: TokenService) { }

  ngOnInit(): void {
  }

  onSubmit(): void{
      this.authService.login(this.form).subscribe(
        res => {
          //console.log(res.jwt_token)
          this.tokenService.saveToken(res.jwt_token)
        },
        error => console.log(error)
    )
  }

}
