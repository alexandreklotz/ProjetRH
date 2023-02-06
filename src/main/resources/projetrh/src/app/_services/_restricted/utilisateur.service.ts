import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {lastValueFrom, map, Observable, switchMap} from "rxjs";
import {Utilisateur} from "../../_models/utilisateur.model";


@Injectable({
  providedIn: 'root'
})

export class UtilisateurService{

  utilisateursList!: Utilisateur[]

  constructor(private http: HttpClient){}


  async getAllUtilisateur(): Promise<Utilisateur[]>{
    let response = await lastValueFrom(this.http.get<Utilisateur[]>('http://localhost:8080/admin/utilisateur/all'))
    this.utilisateursList = response
    return this.utilisateursList
  }

  async getAllCandidats(): Promise<Utilisateur[]> {
    let response = await lastValueFrom(this.http.get<Utilisateur[]>('http://localhost:8080/moderateur/utilisateur/candidats'))
    this.utilisateursList = response
    return this.utilisateursList
  }

  getUtilisateurById(userId: any): Observable<Utilisateur>{
    return this.http.get<Utilisateur>(`http://localhost:8080/admin/utilisateur/id/${encodeURIComponent(userId)}`)
  }

  getUtilisateurByLogin(userLogin: string): Observable<Utilisateur>{
    return this.http.get<Utilisateur>(`http://localhost:8080/admin/utilisateur/login/${userLogin}`)
  }

  createUtilisateur(data: any, headers : {headers: HttpHeaders}): Observable<any> {
    return this.http.post('http://localhost:8080/admin/utilisateur/create', data, headers)
  }

  createCandidat(data: any, headers : { headers: HttpHeaders }): Observable<any>{
    return this.http.post('http://localhost:8080/moderateur/utilisateur/candidat/create', data, headers)
  }

  updateUtilisateur(utilisateur: Utilisateur): Observable<Utilisateur> {
    return this.http.put<Utilisateur>('http://localhost:8080/admin/utilisateur/update', utilisateur)
  }

  deleteUtilisateur(userId: String): void {
    this.http.delete<Utilisateur>(`http://localhost:8080/admin/utilisateur/delete/${userId}`)
  }

}
