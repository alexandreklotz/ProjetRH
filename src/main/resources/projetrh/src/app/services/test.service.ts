import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";
import {Test} from "../models/test.model";


@Injectable({
  providedIn: 'root'
})

export class TestService {

  constructor(private http: HttpClient){}

  getAllTest(): Observable<Test[]>{
    return this.http.get<Test[]>('http://localhost:8080/admin/test/all')
  }

  getTestById(testId: string): Observable<Test>{
    return this.http.get<Test>(`http://localhost:8080/admin/test/${testId}`)
  }

  getTestByCandidat(userId: string): Observable<Test[]>{
    return this.http.get<Test[]>(`http://localhost:8080/admin/test/candidat/${userId}`)
  }

}
