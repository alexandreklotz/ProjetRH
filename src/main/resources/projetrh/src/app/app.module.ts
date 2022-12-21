import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppRoutingModule} from "./app-routing.module";
import { AppComponent } from './app.component';
import { DashboardComponent } from './candidat/dashboard/dashboard.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import {TokenInterceptorProvider} from "./_helpers/token.interceptor";
import { SidebarComponent } from './sidebar/sidebar.component';
import { TestComponent } from './_components/test/test.component';
import { TestListComponent } from './_components/test-list/test-list.component';
import { UtilisateurComponent } from './_components/utilisateur/utilisateur.component';
import { UtilisateurListComponent } from './_components/utilisateur-list/utilisateur-list.component';



@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    SidebarComponent,
    TestComponent,
    TestListComponent,
    UtilisateurComponent,
    UtilisateurListComponent,
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
