import {Question} from "./question.model";
import {Test} from "./test.model";

export class Reponse {

  id!: string;
  texte!: string;
  correctAnswer!: boolean;
  question!: Question;
  tests!: Array<Test>

  constructor(id: string, texte: string, correctAnswer: boolean, question: Question, tests: Array<Test>){
    this.id = id;
    this.texte = texte;
    this.correctAnswer = correctAnswer;
    this.question = question;
    this.tests = tests;
  }

}
