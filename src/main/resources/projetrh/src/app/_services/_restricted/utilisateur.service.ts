import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
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

  getUtilisateurById(userId: string): Observable<Utilisateur>{
    return this.http.get<Utilisateur>(`http://localhost:8080/admin/utilisateur/id/${userId}`)
  }

  getUtilisateurByLogin(userLogin: string): Observable<Utilisateur>{
    return this.http.get<Utilisateur>(`http://localhost:8080/admin/utilisateur/login/${userLogin}`)
  }

  createUtilisateur(utilisateur: Utilisateur): void {
    this.http.post<Utilisateur>('http://localhost:8080/admin/utilisateur/create', utilisateur)
  }

  updateUtilisateur(utilisateur: Utilisateur): void {
    this.http.put<Utilisateur>('http://localhost:8080/admin/utilisateur/update', utilisateur)
  }

  deleteUtilisateur(userId: String): void {
    this.http.delete<Utilisateur>(`http://localhost:8080/admin/utilisateur/delete/${userId}`)
  }

}
