export interface QuestionInterface {

  texte: string,
  tempsReponse: number,
  points: number,
  qcm : {
    id: string
  }/*,
  reponses : {
    texte: string,
    isCorrectAnswer: boolean
  }[]*/

}
