import {Test} from "./test.model";
import {Question} from "./question.model";

export interface Qcm {

  id: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  titre: string;
  questions: Array<Question>;
  Test: Test
}
