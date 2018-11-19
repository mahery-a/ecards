import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../authentication-oauth2.service';

@Component({
  selector: 'app-login-oauth2',
  template: `
  <h2>Login</h2>
  <form *ngIf="!this.authenticationService.isLoggedIn()" [formGroup]="loginForm" (ngSubmit)="onSubmit()">
      <div class="form-group">
          <label for="username">Username</label>
          <input type="text" formControlName="username" class="form-control" [ngClass]="{ 'is-invalid': submitted && f.username.errors }" />
          <div *ngIf="submitted && f.username.errors" class="invalid-feedback">
              <div *ngIf="f.username.errors.required">Username is required</div>
          </div>
      </div>
      <div class="form-group">
          <label for="password">Password</label>
          <input type="password" formControlName="password" class="form-control"
          [ngClass]="{ 'is-invalid': submitted && f.password.errors }" />
          <div *ngIf="submitted && f.password.errors" class="invalid-feedback">
              <div *ngIf="f.password.errors.required">Password is required</div>
          </div>
      </div>
      <div class="form-group">
          <button [disabled]="loading" class="btn btn-primary">Login</button>
          <img *ngIf="loading" src="data:image/gif;base64,
          R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/
          C05FVFNDQVBFMi4wAwEAAAAh/
          hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgA
          AACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+d
          IAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1
          xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMa
          oKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DY
          lJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6Twf
          wyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSl
          KAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAAL
          AAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
      </div>
      <div *ngIf="error" class="alert alert-danger">{{error}}</div>
  </form>
  <div *ngIf="this.authenticationService.isLoggedIn()" class="alert alert-success">You are successfully logged in</div>
  <button *ngIf="this.authenticationService.isLoggedIn()"
          (click)="this.authenticationService.logout()"
          class="btn btn-primary">Logout</button>
  `,
  styles: []
})
export class LoginOauth2Component implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';
  success = '';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    public authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          // success: we've received the token
          // console.log('TOKEN:' + data.access_token);
          this.error = '';
          this.loading = false;

          // if success we can redirect the user
          // this.router.navigate([this.returnUrl]);
          // Get the redirect URL from our auth service
          // If no redirect has been set, use the default
         /* const redirect = this.authenticationService.redirectUrl ? this.authenticationService.redirectUrl : '/admin';
          // Set our navigation extras object
          // that passes on our global query params and fragment
          const navigationExtras: NavigationExtras = {
            queryParamsHandling: 'preserve',
            preserveFragment: true
          };
          // Redirect the user
          this.router.navigate([redirect], navigationExtras);*/
        },
        error => {
          this.error = error.status + ' ' + error.error.error + ': ' + error.error.error_description;
          this.loading = false;
        });
  }
}

