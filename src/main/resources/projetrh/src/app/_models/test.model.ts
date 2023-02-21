import {Qcm} from "./qcm.model";
import {Utilisateur} from "./utilisateur.model";
import {Reponse} from "./reponse.model";
import {Question} from "./question.model";

export class Test {

  id!: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  titre!: string;
  score!: number;
  alreadySubmitted!: boolean;
  qcm!: Qcm;
  utilisateur!: Utilisateur;
  questions!: Question[]

  constructor(id: string, titre: string, score: number, alreadySubmitted: boolean, qcm: Qcm, utilisateur: Utilisateur, questions: Question[]){
    this.id = id;
    this.titre = titre;
    this.score = score;
    this.alreadySubmitted = alreadySubmitted;
    this.qcm = qcm;
    this.utilisateur = utilisateur;
    this.questions = questions;
  }

}
