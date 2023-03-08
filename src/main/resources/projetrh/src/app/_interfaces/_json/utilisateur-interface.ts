export interface UtilisateurInterface {

  id?: string,
  firstName: string
  lastName: string
  userLogin: string
  mailAddress: string
  userPassword?: string
  userDescription: string
  role : {
    id: string
  }


}
