import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private router: Router) { }

  saveToken(token: string): void {
    localStorage.setItem('jwt_token', token)
    this.router.navigate(['dashboard'])
  }

  isLoggedIn(): boolean {
    const jwt_token = localStorage.getItem('jwt_token')
    //console.log(jwt_token)
    return !! jwt_token
  }

  clearToken(): void {
    localStorage.removeItem('jwt_token')
    this.router.navigate([''])
  }

}
