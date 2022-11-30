import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HomepageComponent} from "./homepage/homepage.component";
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


const routes: Routes = [
  //TODO : Voir si il faut spécifier par exemple question/ avant reponses ou reponse/:id comme on récupère les réponses
  // d'une question. Ou qcm/id/questions. Il faudra sûrement modifier le routing.
  {path: 'login', component:LoginComponent},
  {path: '', component:LoginComponent},
  {path: 'dashboard', component: HomepageComponent},
  {path: 'qcm', component:QcmListComponent},
  {path: 'qcm/:id', component:SingleQcmComponent},
  {path: 'tests', component:TestListComponent},
  {path: 'test/:id', component:SingleTestComponent},
  {path: 'questions', component: QuestionListComponent},
  {path: 'question/:id', component:SingleQuestionComponent},
  {path: 'reponses', component: ReponseListComponent},
  {path: 'reponse/:id', component: SingleReponseComponent},
  {path: 'utilisateurs', component:UtilisateurListComponent},
  {path: 'utilisateur/:id', component:SingleUtilisateurComponent}
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
