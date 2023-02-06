import {Qcm} from "./qcm.model";
import {Reponse} from "./reponse.model";

export class Question {

  id!: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  texte!: string;
  tempsReponse!: number;
  qcm!: Qcm;
  reponses!: Array<Reponse>
  points!: number

  constructor(id: string, texte: string, tempsReponse: number, qcm: Qcm, points: number, reponses: Array<Reponse>){
    this.id = id;
    this.texte = texte;
    this.tempsReponse = tempsReponse;
    this.qcm = qcm;
    this.points = points;
    this.reponses = reponses;
  }

}
