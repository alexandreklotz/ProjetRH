import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";
import {Reponse} from "../_models/reponse.model";

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

  getReponseByQuestion(questionId: string): Observable<Reponse[]>{
    return this.http.get<Reponse[]>(`http://localhost:8080/admin/reponse/question/${questionId}`)
  }

}
