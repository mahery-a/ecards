import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { NotfoundComponent } from './404/not-found.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';

import { AuthenticationRoutes } from './authentication.routing';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from '../login-oauth2/jwt.interceptor';
import { ErrorInterceptor } from '../login-oauth2/error.interceptor';
import { CookieService } from 'ngx-cookie-service';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AuthenticationRoutes),
  ],
  declarations: [
    NotfoundComponent,
    LoginComponent,
    SignupComponent
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    CookieService
  ],
})
export class AuthenticationModule {}
