import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class RolesService{

  constructor(private http: HttpClient){}

  //TODO : Implémenter les get pour récupérer les rôles.
}
