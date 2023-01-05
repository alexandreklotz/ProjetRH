import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "./candidat/dashboard/dashboard.component";
import {TestListComponent} from "./_components/test-list/test-list.component";
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./_helpers/auth.guard";
import {UtilisateurListComponent} from "./_components/utilisateur-list/utilisateur-list.component";
import {SingleUtilisateurComponent} from "./_components/single-utilisateur/single-utilisateur.component";
import {AdminPanelComponent} from "./admin/adminpanel/admin-panel.component";
import {RecruteurPanelComponent} from "./recruteur/recruteur-panel/recruteur-panel.component";
import {CreateCandidatFormComponent} from "./_components/_forms/create-candidat-form/create-candidat-form.component";
import {CreateTestFormComponent} from "./_components/_forms/create-test-form/create-test-form.component";


const routes: Routes = [
  //TODO : Voir si il faut spécifier par exemple question/ avant reponses ou reponse/:id comme on récupère les réponses
  // d'une question. Ou qcm/id/questions. Il faudra sûrement modifier le routing.
  {path: 'login', component:LoginComponent},
  {path: '', component:LoginComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard], data : { allowedRoles : "CANDIDAT"}},
  {path: 'tests', component:TestListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'utilisateurs', component:UtilisateurListComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}},
  {path: 'utilisateur/:id', component:SingleUtilisateurComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}},
  {path: 'admin', component: AdminPanelComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}},
  {path: 'recruteur', component: RecruteurPanelComponent, canActivate:[AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'candidat/nouveau', component: CreateCandidatFormComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'test/nouveau', component: CreateTestFormComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}}
  /*{path: 'qcm', component:QcmListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'qcm/:id', component:SingleQcmComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'test/:id', component:SingleTestComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN", "CANDIDAT"]}},
  {path: 'questions', component: QuestionListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'question/:id', component:SingleQuestionComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'reponses', component: ReponseListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'reponse/:id', component: SingleReponseComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'utilisateur/:id', component:SingleUtilisateurComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}}*/
  //TODO : Créer des routes pour les rôles ? voir comment gérer utilisateur/:id/role/:id ?
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
