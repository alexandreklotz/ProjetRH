import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";
import {Reponse} from "../../_models/reponse.model";

@Injectable({
  providedIn: 'root'
})

export class ReponseService {

  constructor(private http: HttpClient){}

  getAllReponses(): Observable<Reponse[]>{
    return this.http.get<Reponse[]>('http://localhost:8080/admin/reponse/all')
  }

  getReponseById(reponseId: string): Observable<Reponse>{
    return this.http.get<Reponse>(`http://localhost:8080/admin/reponse/${reponseId}`)
  }

  getReponseByQuestion(questionId: any): Observable<Reponse[]>{
    return this.http.get<Reponse[]>(`http://localhost:8080/moderateur/reponse/question/${encodeURIComponent(questionId)}`)
  }

  createNewReponseInQuestion(data: any, questionId: string, headers : {headers : HttpHeaders}): Observable<any> {
    return this.http.post(`http://localhost:8080/moderateur/reponse/create/${encodeURIComponent(questionId)}`, data, headers)
  }

  deleteReponse(reponseId: string, headers : {headers : HttpHeaders}) {
    this.http.delete(`http://localhost:8080/moderateur/reponse/delete/${encodeURIComponent(reponseId)}`)
  }

}
