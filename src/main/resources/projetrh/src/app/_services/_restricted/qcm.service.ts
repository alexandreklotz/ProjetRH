import {Injectable} from "@angular/core";
import {Qcm} from "../../_models/qcm.model";
import {HttpClient} from "@angular/common/http";
import {map, Observable, switchMap} from "rxjs";

@Injectable({
  providedIn:'root'
})

export class QcmService {

  constructor(private http: HttpClient) {}

  getAllQcm():Observable<Qcm[]>{
    return this.http.get<Qcm[]>('http://localhost:8080/admin/qcm/all')
  }

  getQcmById(qcmId: string): Observable<Qcm>{
    return this.http.get<Qcm>(`http://localhost:8080/admin/qcm/${qcmId}`)
  }

}
