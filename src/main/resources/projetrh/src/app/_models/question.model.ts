import {Qcm} from "./qcm.model";
import {Reponse} from "./reponse.model";

export class Question {

  id: string;
  texte!: string;
  tempsReponse!: number;
  qcm?: Qcm;
  reponses!: Array<Reponse>
  points!: number

  constructor(id: string, texte: string, tempsReponse: number, points: number, reponses: Array<Reponse>,qcm?: Qcm){
    this.id = id;
    this.texte = texte;
    this.tempsReponse = tempsReponse;
    this.qcm = qcm;
    this.points = points;
    this.reponses = reponses;
  }

}
