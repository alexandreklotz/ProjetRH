import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {TokenService} from "../_services/token.service";
import {Roles} from "../_models/roles.model";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router,
              private tokenService: TokenService){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const userToken = this.tokenService.getToken()

    if(!userToken || !this.tokenService.isLoggedIn()){
      this.router.navigate(['login'])
      return false;

    }

    const userRole = this.tokenService.getRoleFromToken(userToken)
    const allowedRoles = route.data['allowedRoles']


    if (Array.isArray(allowedRoles)) {
      let hasMatch = false;
      allowedRoles.forEach((rolename: string) => {
        if (rolename.match(userRole)) {
          hasMatch = true;
        }
      });
      if (hasMatch) {
        return true;
      } else {
        this.router.navigate(['login']);
        return false;
      }
    } else if (typeof allowedRoles === 'string') {
      if (allowedRoles.includes(userRole)) {
        return true;
      } else {
        this.router.navigate(['login']);
        return false;
      }
    }

    this.router.navigate(['login']) //TODO : créer un component unauthorized et remplacer login par ce dernier
    return false;

  }

}
