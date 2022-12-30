import { Injectable } from '@angular/core';
import {lastValueFrom, Observable} from "rxjs";
import {Utilisateur} from "../_models/utilisateur.model";
import {HttpClient} from "@angular/common/http";
import {Test} from "../_models/test.model";

@Injectable({
  providedIn: 'root'
})
export class UserEndPointService {

  constructor(private http: HttpClient) { }

  utilisateur!: Utilisateur
  retrievedTest!: Test
  assignedTests!: Test[]

  async userSelfRetrieve(): Promise<Utilisateur> {

    let response = await lastValueFrom(this.http.get<Utilisateur>('http://localhost:8080/user/retrieve'))
    this.utilisateur = response
    return this.utilisateur
  }

  userSubmitTest(test: Test): void {
    this.http.post<Test>('http://localhost:8080/user/test/submit', test)
  }

  userRetrieveSingleTest(testId: string): Observable<Test>{
    return this.http.get<Test>(`http://localhost:8080/user/test/id/${testId}`)
  }

  async userRetrieveAssignedTests(): Promise<Test[]>{

    let response = await lastValueFrom(this.http.get<Test[]>('http://localhost:8080/user/test/ownedtests'))
    this.assignedTests = response
    return this.assignedTests

  }

}
