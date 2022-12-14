import {Question} from "./question.model";
import {Test} from "./test.model";

export class Reponse {

  id!: string; //TODO : Vérifier pour les UUID, je les ai mis en tant que String pour le moment, à vérifier.
  texte!: string;
  correctAnswer!: boolean;
  points!: number;
  question!: Question;
  tests!: Array<Test>

  constructor(id: string, texte: string, correctAnswer: boolean, points: number, question: Question, tests: Array<Test>){
    this.id = id;
    this.texte = texte;
    this.correctAnswer = correctAnswer;
    this.points = points;
    this.question = question;
    this.tests = tests;
  }

}
