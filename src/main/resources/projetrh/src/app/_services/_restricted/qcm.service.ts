import {Injectable} from "@angular/core";
import {Qcm} from "../../_models/qcm.model";
import {HttpClient} from "@angular/common/http";
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

}
