import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppRoutingModule} from "./app-routing.module";
import { AppComponent } from './app.component';
import { DashboardComponent } from './candidat/dashboard/dashboard.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import {TokenInterceptorProvider} from "./_helpers/token.interceptor";
import { HeaderComponent } from './_uielements/header/header.component';
import { TestListComponent } from './_components/test-list/test-list.component';
import { UtilisateurListComponent } from './_components/utilisateur-list/utilisateur-list.component';
import { SingleUtilisateurComponent } from './_components/single-utilisateur/single-utilisateur.component';
import { AdminPanelComponent } from './admin/adminpanel/admin-panel.component';
import { SingleTestComponent } from './_components/single-test/single-test.component';
import { RecruteurPanelComponent } from './recruteur/recruteur-panel/recruteur-panel.component';
import { CreateCandidatFormComponent } from './_components/_forms/create-candidat-form/create-candidat-form.component';
import { CreateUtilisateurFormComponent } from './_components/_forms/create-utilisateur-form/create-utilisateur-form.component';
import { CreateTestFormComponent } from './_components/_forms/create-test-form/create-test-form.component';
import { CreateQuestionFormComponent } from './_components/_forms/create-question-form/create-question-form.component';
import { CreateQcmFormComponent } from './_components/_forms/create-qcm-form/create-qcm-form.component';
import { CandidatListComponent } from './_components/candidat-list/candidat-list.component';
import { QuestionListComponent } from './_components/question-list/question-list.component';
import { QcmListComponent } from './_components/qcm-list/qcm-list.component';
import { SingleQcmComponent } from './_components/single-qcm/single-qcm.component';
import { SingleQuestionComponent } from './_components/single-question/single-question.component';
import { SingleCandidatComponent } from './_components/single-candidat/single-candidat.component';



@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    HeaderComponent,
    TestListComponent,
    UtilisateurListComponent,
    SingleUtilisateurComponent,
    AdminPanelComponent,
    SingleTestComponent,
    RecruteurPanelComponent,
    CreateCandidatFormComponent,
    CreateUtilisateurFormComponent,
    CreateTestFormComponent,
    CreateQuestionFormComponent,
    CreateQcmFormComponent,
    CandidatListComponent,
    QuestionListComponent,
    QcmListComponent,
    SingleQcmComponent,
    SingleQuestionComponent,
    SingleCandidatComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
  providers: [
    TokenInterceptorProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
