import {Injectable} from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private router: Router) { }

  saveToken(token: string): void {
    localStorage.setItem('jwt_token', token)
    const userRole = this.getRoleFromToken(token)
    //this.router.navigate(['dashboard'])
    this.redirectUserBasedOnRole(userRole)
  }

  isLoggedIn(): boolean {
    const jwt_token = localStorage.getItem('jwt_token')
    //console.log(jwt_token)
    return !! jwt_token
  }

  clearToken(): void {
    localStorage.removeItem('jwt_token')
    this.router.navigate(['login'])
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token')
  }

  getRoleFromToken(token: string): string {
    if(!token){
      return ''
    }
    // @ts-ignore
    const userToken = this.getToken().split('.')[1];
    const tokenPayload = JSON.parse(window.atob(userToken))

    return tokenPayload.role;
  }


  redirectUserBasedOnRole(role: string): void {
    if(role.includes("CANDIDAT")){
      this.router.navigate(['dashboard'])
    }
    else if(role.includes("RECRUTEUR")){
      this.router.navigate(['tests'])
    }
    else if(role.includes("ADMIN")){
      this.router.navigate(['utilisateurs'])
    }
    else {
      this.router.navigate(['login']) //changer en unauthorized
    }
  }


}
