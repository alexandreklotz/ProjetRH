import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "./candidat/dashboard/dashboard.component";
import {QcmListComponent} from "./qcm-list/qcm-list.component";
import {QcmComponent} from "./qcm/qcm.component";
import { UtilisateurComponent } from './utilisateur/utilisateur.component';
import { TestComponent } from './test/test.component';
import { UtilisateurListComponent } from './utilisateur-list/utilisateur-list.component';
import { TestListComponent } from './test-list/test-list.component';
import { QuestionComponent } from './question/question.component';
import { QuestionListComponent } from './question-list/question-list.component';
import { ReponseComponent } from './reponse/reponse.component';
import { ReponseListComponent } from './reponse-list/reponse-list.component';
import { SingleQcmComponent } from './single-qcm/single-qcm.component';
import { SingleUtilisateurComponent } from './single-utilisateur/single-utilisateur.component';
import { SingleTestComponent } from './single-test/single-test.component';
import { RolesComponent } from './roles/roles.component';
import { RolesListComponent } from './roles-list/roles-list.component';
import { SingleRolesComponent } from './single-roles/single-roles.component';
import { SingleQuestionComponent } from './single-question/single-question.component';
import { SingleReponseComponent } from './single-reponse/single-reponse.component';
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./_helpers/auth.guard";


const routes: Routes = [
  //TODO : Voir si il faut spécifier par exemple question/ avant reponses ou reponse/:id comme on récupère les réponses
  // d'une question. Ou qcm/id/questions. Il faudra sûrement modifier le routing.
  {path: 'login', component:LoginComponent},
  {path: '', component:LoginComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard], data : { allowedRoles : "CANDIDAT"}},
  {path: 'qcm', component:QcmListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'qcm/:id', component:SingleQcmComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'tests', component:TestListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'test/:id', component:SingleTestComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN", "CANDIDAT"]}},
  {path: 'questions', component: QuestionListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'question/:id', component:SingleQuestionComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'reponses', component: ReponseListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'reponse/:id', component: SingleReponseComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'utilisateurs', component:UtilisateurListComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}},
  {path: 'utilisateur/:id', component:SingleUtilisateurComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}}
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
