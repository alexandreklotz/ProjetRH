import {Test} from "./test.model";
import {Question} from "./question.model";

export class Qcm {

  id!: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  titre!: string;
  questions?: Array<Question>;
  tests!: Array<Test>

  constructor(id: string, titre: string, tests: Array<Test>, questions?: Array<Question>){
    this.id = id;
    this.titre = titre;
    this.questions = questions;
    this.tests = tests;
  }
}
