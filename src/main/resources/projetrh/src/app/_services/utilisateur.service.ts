import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";
import {Utilisateur} from "../_models/utilisateur.model";


@Injectable({
  providedIn: 'root'
})

export class UtilisateurService{

  constructor(private http: HttpClient){}

  createUserAfterRetrieve(utilisateur: Utilisateur){
    //voir si cette méthode est nécessaire
  }

  userSelfRetrieve(): Observable<Utilisateur> {
    return this.http.get<Utilisateur>('http://localhost:8080/user/retrieve')
  }

  getAllUtilisateur(): Observable<Utilisateur[]>{
    return this.http.get<Utilisateur[]>('http://localhost:8080/admin/utilisateur/all')
  }

  getUtilisateurById(userId: string): Observable<Utilisateur>{
    return this.http.get<Utilisateur>(`http://localhost:8080/admin/utilisateur/id/${userId}`)
  }

  getUtilisateurByLogin(userLogin: string): Observable<Utilisateur>{
    return this.http.get<Utilisateur>(`http://localhost:8080/admin/utilisateur/login/${userLogin}`)
  }

}
