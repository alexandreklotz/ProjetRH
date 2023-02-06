import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Roles} from "../../_models/roles.model";
import {lastValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class RolesService{

  rolesList$!: Roles[]

  constructor(private http: HttpClient){}

  async getAllRoles(): Promise<Roles[]> {
    let response = await lastValueFrom(this.http.get<Roles[]>('http://localhost:8080/admin/roles/all'))
    this.rolesList$ = response
    return this.rolesList$
  }

}
