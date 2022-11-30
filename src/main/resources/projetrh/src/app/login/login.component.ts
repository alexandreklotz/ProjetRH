import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username!: string;
  password!: string;
  errorMessage = 'Login/Mot de passe incorrect.';
  successMessage!: string;
  invalidLogin = false;
  loginSuccess = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }

  handleLogin() {
    this.authService.login(this.username, this.password).subscribe((result) => {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = "Login OK";
      this.router.navigate(['http://localhost:8080/logintest']);
      console.log("Login OK");
      }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
      console.log("Login NOK");
      }
    )
  }

}
