import {Test} from "./test.model";
import {Roles} from "./roles.model";

export class Utilisateur {

  id!: string;
  firstName!: string;
  lastName!: string;
  userLogin!: string;
  userPassword!: string;
  userDescription!: string;
  mailAddress!: string;
  globalScore!: number;

  tests!: Array<Test>;
  role!: Roles

  constructor(id: string,
              firstName: string,
              lastName: string,
              userLogin: string,
              userPassword: string,
              userDescription: string,
              mailAddress: string,
              globalScore: number,
              tests: Array<Test>,
              role: Roles){

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userLogin = userLogin;
    this.userPassword = userPassword;
    this.userDescription = userDescription;
    this.mailAddress = mailAddress;
    this.globalScore = globalScore;
    this.tests = tests;
    this.role = role;
  }

}
