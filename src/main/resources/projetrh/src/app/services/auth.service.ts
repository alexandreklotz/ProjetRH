import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map} from 'rxjs/operators'

@Injectable({
  providedIn:'root'
})

export class AuthService {

  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

  public username!: String;
  public password!: String;

  constructor(private http: HttpClient){ }

  login(username: string, password: string){
    return this.http.get(`http://localhost:8080/basicauth`,
      { headers: { authorization: this.createBasicAuthToken(username, password) } }).pipe(map((res) => {
      this.username = username;
      this.password = password;
      this.registerSuccessfulLogin(username, password);
    }));
  }

  createBasicAuthToken(username: String, password: String){
    return 'Basic' + window.btoa(username + ":" + password)
  }

  registerSuccessfulLogin(username: string, password: string){
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username)
  }

  logout(){
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this.username = '';
    this.password = '';
  }

  isUserLoggingIn(){
    let utilisateur = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (utilisateur == null) return false;
    return true;
  }

  getLoggedInUserName(){
    let utilisateur = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (utilisateur == null) return ''
    return utilisateur
  }

}
