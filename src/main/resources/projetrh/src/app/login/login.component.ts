import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  model: any = {};
  sessionId: any = "";

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  login(){
    let url = "http://localhost:8080/login";
    this.http.post<any>(url, {
      userLogin: this.model.userLogin,
      userPassword: this.model.userPassword
    }).subscribe(res => {
      if (res) {
        this.sessionId = res.sessionId;

        sessionStorage.setItem('token',
          this.sessionId);
        this.router.navigate(['dashboard']);
      } else {
        alert("Echec du login.")
      }
    });
  }

}
