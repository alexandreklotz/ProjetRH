import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "./candidat/dashboard/dashboard.component";
import {TestListComponent} from "./_components/test-list/test-list.component";
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./_helpers/auth.guard";
import {UtilisateurListComponent} from "./_components/utilisateur-list/utilisateur-list.component";


const routes: Routes = [
  //TODO : Voir si il faut spécifier par exemple question/ avant reponses ou reponse/:id comme on récupère les réponses
  // d'une question. Ou qcm/id/questions. Il faudra sûrement modifier le routing.
  {path: 'login', component:LoginComponent},
  {path: '', component:LoginComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard], data : { allowedRoles : "CANDIDAT"}},
  {path: 'tests', component:TestListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'utilisateurs', component:UtilisateurListComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}}
  /*{path: 'qcm', component:QcmListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'qcm/:id', component:SingleQcmComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'test/:id', component:SingleTestComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN", "CANDIDAT"]}},
  {path: 'questions', component: QuestionListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'question/:id', component:SingleQuestionComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'reponses', component: ReponseListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'reponse/:id', component: SingleReponseComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'utilisateur/:id', component:SingleUtilisateurComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}}*/
  //TODO : Créer des routes pour les rôles ? voir comment gérer utilisateur/:id/role/:id ?
  //TODO : voir comment taper le lien de l'API pour self retrieve l'user ?
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {

}
