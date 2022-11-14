import {Qcm} from "./qcm.model";
import {Reponse} from "./reponse.model";

export interface Question {

  id: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  texte: string;
  tempsReponse: number;
  qcm: Qcm;
  reponses: Array<Reponse>

}
