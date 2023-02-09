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
import {CreateQuestionFormComponent} from "./_components/_forms/create-question-form/create-question-form.component";
import {CreateQcmFormComponent} from "./_components/_forms/create-qcm-form/create-qcm-form.component";
import {QcmListComponent} from "./_components/qcm-list/qcm-list.component";
import {CandidatListComponent} from "./_components/candidat-list/candidat-list.component";
import {SingleCandidatComponent} from "./_components/single-candidat/single-candidat.component";
import {SingleQuestionComponent} from "./_components/single-question/single-question.component";
import {SingleQcmComponent} from "./_components/single-qcm/single-qcm.component";
import {QuestionListComponent} from "./_components/question-list/question-list.component";
import {SingleTestComponent} from "./_components/single-test/single-test.component";
import {
  CreateUtilisateurFormComponent
} from "./_components/_forms/create-utilisateur-form/create-utilisateur-form.component";
import {UnauthorizedComponent} from "./_components/unauthorized/unauthorized.component";
import {UtilisateurSelfUpdateComponent} from "./utilisateur-self-update/utilisateur-self-update.component";


const routes: Routes = [
  //TODO : Voir si il faut spécifier par exemple question/ avant reponses ou reponse/:id comme on récupère les réponses
  // d'une question. Ou qcm/id/questions. Il faudra sûrement modifier le routing.
  {path: 'login', component:LoginComponent},
  {path: '', component:LoginComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard], data : { allowedRoles : "CANDIDAT"}},
  {path: 'tests', component:TestListComponent, canActivate:[AuthGuard], data : { allowedRoles: ["RECRUTEUR", "ADMIN"]}},
  {path: 'utilisateurs', component:UtilisateurListComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}},
  {path: 'utilisateur/:id', component:SingleUtilisateurComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}},
  {path: 'creer/utilisateur', component:CreateUtilisateurFormComponent, canActivate:[AuthGuard], data : {allowedRoles: "ADMIN"}},
  {path: 'admin', component: AdminPanelComponent, canActivate:[AuthGuard], data : { allowedRoles: "ADMIN"}},
  {path: 'recruteur', component: RecruteurPanelComponent, canActivate:[AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'creer/candidat', component: CreateCandidatFormComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'creer/test', component: CreateTestFormComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'question/:id', component: SingleQuestionComponent, canActivate: [AuthGuard], data : {allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'creer/question', component: CreateQuestionFormComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'creer/qcm', component: CreateQcmFormComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'qcm/:id', component: SingleQcmComponent, canActivate: [AuthGuard], data : {allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'qcms', component: QcmListComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'questions', component: QuestionListComponent, canActivate: [AuthGuard], data : {allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'candidats', component: CandidatListComponent, canActivate: [AuthGuard], data : {allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'candidat/:id', component: SingleCandidatComponent, canActivate: [AuthGuard], data : { allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'test/:id', component: SingleTestComponent, canActivate: [AuthGuard], data : {allowedRoles: ["ADMIN", "RECRUTEUR"]}},
  {path: 'unauthorized', component: UnauthorizedComponent, canActivate: [AuthGuard], data : {allowedRoles: ["ADMIN", "RECRUTEUR", "CANDIDAT"]}},
  {path: 'profilupdate', component: UtilisateurSelfUpdateComponent, canActivate: [AuthGuard], data : {allowedRoles: ["ADMIN", "RECRUTEUR", "CANDIDAT"]}}
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
