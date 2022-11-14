import {Qcm} from "./qcm.model";
import {Utilisateur} from "./utilisateur.model";
import {Reponse} from "./reponse.model";

export interface Test {

  id: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  titre: string;
  score: number;
  alreadySubmitted: boolean;
  qcm: Qcm;
  utilisateur: Utilisateur;
  reponses: Array<Reponse>

}
