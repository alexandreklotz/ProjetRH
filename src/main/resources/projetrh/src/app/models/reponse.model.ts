import {Question} from "./question.model";
import {Test} from "./test.model";

export interface Reponse {

  id: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  texte: string;
  correctAnswer: boolean;
  points: number;
  question: Question;
  Tests: Array<Test>

}
