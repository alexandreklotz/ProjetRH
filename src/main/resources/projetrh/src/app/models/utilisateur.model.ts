import {Test} from "./test.model";
import {Roles} from "./roles.model";

export interface Utilisateur {

  id: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  firstName: string;
  lastName: string;
  userLogin: string;
  userPassword: string;
  mailAddress: string;
  globalScore: number;

  tests: Array<Test>;
  role: Roles
}
