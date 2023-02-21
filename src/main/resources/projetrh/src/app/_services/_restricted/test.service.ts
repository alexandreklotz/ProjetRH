import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {lastValueFrom, map, Observable, switchMap} from "rxjs";
import {Test} from "../../_models/test.model";


@Injectable({
  providedIn: 'root'
})

export class TestService {

  testListe!: Test[]

  constructor(private http: HttpClient){}

  async getAllTest(): Promise<Test[]>{
    let response = await lastValueFrom(this.http.get<Test[]>('http://localhost:8080/moderateur/test/all'))
    this.testListe = response
    return this.testListe
  }

  getTestById(testId: any): Observable<Test>{
    return this.http.get<Test>(`http://localhost:8080/moderateur/test/id/${encodeURIComponent(testId)}`)
  }

  getTestByCandidat(userId: string): Observable<Test[]>{
    return this.http.get<Test[]>(`http://localhost:8080/moderateur/test/candidat/${userId}`)
  }

  createTest(data: any, headers : { headers: HttpHeaders }): Observable<any>{
    return this.http.post('http://localhost:8080/moderateur/test/create', data, headers)
  }

  deleteTest(testId: any){
    this.http.delete(`http://localhost:8080/moderateur/test/delete/${encodeURIComponent(testId)}`)
  }

}
