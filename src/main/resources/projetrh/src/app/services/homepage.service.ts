import {Injectable} from "@angular/core";
import {Homepage} from "../models/homepage.model";
import {HttpClient} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";
import {Utilisateur} from "../models/utilisateur.model";
import {Test} from "../models/test.model";

@Injectable({
  providedIn:'root'
})

export class HomepageService{

  constructor(private http: HttpClient){}

  retrieveUser(): Observable<Utilisateur>{
    return this.http.get<Utilisateur>('http://localhost:8080/user/retrieve')
  }

  retrieveAllCandidatTest(): Observable<Test[]>{
    return this.http.get<Test[]>('http://localhost:8080/user/test/ownedtests')
  }


}
