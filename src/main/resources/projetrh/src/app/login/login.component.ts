import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username!: string;
  userpassword!: string;
  errorMessage = 'Login/Mot de passe incorrect.';
  successMessage!: string;
  invalidLogin = false;
  loginSuccess = false;

  constructor() { }

  ngOnInit(): void {
  }

  handleLogin() {
    console.log("clicked");
  }

}
