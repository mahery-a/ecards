import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginOauth2Component } from './login-oauth2.component';

const routes: Routes = [
  { path: 'login', component: LoginOauth2Component }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginOauth2RoutingModule { }
