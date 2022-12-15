import {Test} from "./test.model";

export class Dashboard { //TODO : voir comment on peut récupérer le fullname de l'utilisateur
  welcomeMsg!: string;
  fullUserName!: string;
  userGlobalScore!: number;
  userTests!: Array<Test>;

  constructor(welcomeMsg: string, fullUserName: string, userGlobalScore: number, userTests: Array<Test>){
    this.welcomeMsg = welcomeMsg;
    this.fullUserName = fullUserName;
    this.userGlobalScore = userGlobalScore;
    this.userTests = userTests;
  }

}
