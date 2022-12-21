import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
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

  userSelfRetrieve(): Utilisateur {
    this.http.get<Utilisateur>('http://localhost:8080/user/retrieve').subscribe(utilisateur =>
      this.utilisateur = utilisateur
  )
    this.utilisateur.tests = this.userRetrieveAssignedTests()
    return this.utilisateur
  }

  userSubmitTest(test: Test): void {
    this.http.post<Test>('http://localhost:8080/user/test/submit', test)
  }

  userRetrieveSingleTest(testId: string): Observable<Test>{
    return this.http.get<Test>(`http://localhost:8080/user/test/id/${testId}`)
  }

  userRetrieveAssignedTests(): Test[]{
    this.http.get<Test[]>('http://localhost:8080/user/test/ownedtests').subscribe(tests =>
      this.assignedTests = tests
    )
    return this.assignedTests
  }

}
