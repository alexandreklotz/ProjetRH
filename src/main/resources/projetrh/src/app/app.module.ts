import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppRoutingModule} from "./app-routing.module";
import { AppComponent } from './app.component';
import { QcmComponent } from './qcm/qcm.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { QcmListComponent } from './qcm-list/qcm-list.component';
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
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { LogoutComponent } from './logout/logout.component';
import { LoginComponent } from './login/login.component';


@NgModule({
  declarations: [
    AppComponent,
    QcmComponent,
    DashboardComponent,
    QcmListComponent,
    UtilisateurComponent,
    TestComponent,
    UtilisateurListComponent,
    TestListComponent,
    QuestionComponent,
    QuestionListComponent,
    ReponseComponent,
    ReponseListComponent,
    SingleQcmComponent,
    SingleUtilisateurComponent,
    SingleTestComponent,
    RolesComponent,
    RolesListComponent,
    SingleRolesComponent,
    SingleQuestionComponent,
    SingleReponseComponent,
    LogoutComponent,
    LoginComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule
    ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
