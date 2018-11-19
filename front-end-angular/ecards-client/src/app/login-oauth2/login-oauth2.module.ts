import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { LoginOauth2Component } from './login-oauth2.component';
import { JwtInterceptor } from './jwt.interceptor';
import { ErrorInterceptor } from './error.interceptor';
import { LoginOauth2RoutingModule } from './login-oauth2-routing.module';

@NgModule({
  imports: [
    ReactiveFormsModule,
    CommonModule,
    LoginOauth2RoutingModule
  ],
  declarations: [LoginOauth2Component],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    CookieService
  ],
})
export class LoginOauth2Module {}
