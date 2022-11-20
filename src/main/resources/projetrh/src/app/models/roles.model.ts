import {Utilisateur} from "./utilisateur.model";

export class Roles {

  id!: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  denomination!: string;
  utilisateurs!: Array<Utilisateur>

  constructor(id: string, denomination: string, utilisateurs: Array<Utilisateur>){
    this.id = id;
    this.denomination = denomination;
    this.utilisateurs = utilisateurs;
  }

}
