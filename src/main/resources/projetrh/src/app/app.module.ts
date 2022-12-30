import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppRoutingModule} from "./app-routing.module";
import { AppComponent } from './app.component';
import { DashboardComponent } from './candidat/dashboard/dashboard.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import {TokenInterceptorProvider} from "./_helpers/token.interceptor";
import { HeaderComponent } from './_uielements/header/header.component';
import { TestListComponent } from './_components/test-list/test-list.component';
import { UtilisateurListComponent } from './_components/utilisateur-list/utilisateur-list.component';
import { SingleUtilisateurComponent } from './_components/single-utilisateur/single-utilisateur.component';
import { UtilisateurFormComponent } from './_components/_forms/utilisateur-form/utilisateur-form.component';
import { AdminPanelComponent } from './admin/adminpanel/admin-panel.component';
import { SingleTestComponent } from './_components/single-test/single-test.component';



@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    HeaderComponent,
    TestListComponent,
    UtilisateurListComponent,
    SingleUtilisateurComponent,
    UtilisateurFormComponent,
    AdminPanelComponent,
    SingleTestComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule
    ],
  providers: [
    TokenInterceptorProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
