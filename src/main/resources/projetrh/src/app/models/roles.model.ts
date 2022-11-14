import {Utilisateur} from "./utilisateur.model";

export interface Roles {

  id: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  denomination: string;
  utilisateurs: Array<Utilisateur>

}
