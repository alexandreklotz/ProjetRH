import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HTTP_INTERCEPTORS
} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {TokenService} from "../_services/token.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    const jwttoken = this.tokenService.getToken()

    if(jwttoken !== null){
      let clone = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + jwttoken)
      })
      return next.handle(clone).pipe(
        catchError(err => {
          console.log(err)
          if(err.status === 401){
            this.tokenService.clearToken()
          }
          return throwError('Session expirée')
        })
      )
    }

    return next.handle(request);
  }
}

export const TokenInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: TokenInterceptor,
  multi: true
}
