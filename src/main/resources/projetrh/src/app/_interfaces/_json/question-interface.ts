import {ReponseDto} from "../../_models/_dto/reponse-dto";
import {Qcm} from "../../_models/qcm.model";

export interface QuestionInterface {

  texte: string,
  tempsReponse: number,
  points: number,
  qcm: {
    id: string
  },
  reponses : ReponseDto[]

}
