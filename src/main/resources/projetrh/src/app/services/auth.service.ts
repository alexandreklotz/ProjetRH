import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map} from 'rxjs/operators'

@Injectable({
  providedIn:'root'
})

export class AuthService {

  public username!: string;
  public userpassword!: string;

  constructor(private http: HttpClient){ }

  login(username: string, password: string){
    //return http.get vers le lien de l'api pour login
  }

}
