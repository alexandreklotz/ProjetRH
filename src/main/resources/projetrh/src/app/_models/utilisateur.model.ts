import {Test} from "./test.model";
import {Roles} from "./roles.model";

export class Utilisateur {

  id!: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  firstName!: string;
  lastName!: string;
  userLogin!: string;
  userPassword!: string;
  mailAddress!: string;
  globalScore!: number;

  tests!: Array<Test>;
  role!: Roles

  constructor(id: string,
              firstName: string,
              lastName: string,
              userLogin: string,
              userPassword: string,
              mailAddress: string,
              globalScore: number,
              tests: Array<Test>,
              role: Roles){

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userLogin = userLogin;
    this.userPassword = userPassword;
    this.mailAddress = mailAddress;
    this.globalScore = globalScore;
    this.tests = tests;
    this.role = role;
  }
}
