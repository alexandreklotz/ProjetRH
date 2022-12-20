import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";
import {Test} from "../_models/test.model";


@Injectable({
  providedIn: 'root'
})

export class TestService {

  constructor(private http: HttpClient){}

  getAllTest(): Observable<Test[]>{
    return this.http.get<Test[]>('http://localhost:8080/moderateur/test/all')
  }

  getTestById(testId: string): Observable<Test>{
    return this.http.get<Test>(`http://localhost:8080/moderateur/test/${testId}`)
  }

  getTestByCandidat(userId: string): Observable<Test[]>{
    return this.http.get<Test[]>(`http://localhost:8080/moderateur/test/candidat/${userId}`)
  }

}
