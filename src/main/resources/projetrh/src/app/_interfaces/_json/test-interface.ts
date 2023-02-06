import {Utilisateur} from "../../_models/utilisateur.model";

export interface TestInterface {

  titre: string;
  qcmId: string;
  utilisateur: {
    id : string;
  }

}
