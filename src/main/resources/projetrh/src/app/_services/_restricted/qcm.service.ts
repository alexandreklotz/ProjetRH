import {Injectable} from "@angular/core";
import {Qcm} from "../../_models/qcm.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {lastValueFrom, map, Observable, switchMap} from "rxjs";

@Injectable({
  providedIn:'root'
})

export class QcmService {

  qcmList!: Qcm[]

  constructor(private http: HttpClient) {}

  async getAllQcm(): Promise<Qcm[]>{
    let response = await lastValueFrom(this.http.get<Qcm[]>('http://localhost:8080/moderateur/qcm/all'))
    this.qcmList = response
    return this.qcmList
  }

  getQcmById(qcmId: any): Observable<Qcm>{
    return this.http.get<Qcm>(`http://localhost:8080/moderateur/qcm/id/${encodeURIComponent(qcmId)}`)
  }

  updateQcm(qcm: Qcm): Observable<Qcm>{
    return this.http.put<Qcm>('http://localhost:8080/moderateur/qcm/update', qcm)
  }

  createQcm(data: any, headers : {headers: HttpHeaders}): Observable<Qcm>{
    return this.http.post<Qcm>('http://localhost:8080/moderateur/qcm/create', data, headers)
  }

  deleteQcm(qcmId: any){
    this.http.delete(`http://localhost:8080/moderateur/qcm/delete/${encodeURIComponent(qcmId)}`).subscribe()
  }

}
