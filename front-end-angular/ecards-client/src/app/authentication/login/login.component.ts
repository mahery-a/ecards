import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/authentication-oauth2.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public authenticationService: AuthenticationService) { }

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';
  success = '';
  loginform = true;
  recoverform = false;

  showRecoverForm() {
    this.loginform = !this.loginform;
    this.recoverform = !this.recoverform;
  }

  ngOnInit() {
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  onSubmit(username: string, password: string) {
    event.preventDefault();
    console.log('onSubmit ' + username);

    // stop here if form is invalid
    if (username === '' || password === '') {
      this.error = 'fields are invalid';
      return;
    }

    console.log('form is valid');

    this.loading = true;
    this.authenticationService.login(username, password)
      .pipe(first())
      .subscribe(
        data => {
          // success: we've received the token
          // console.log('TOKEN:' + data.access_token);
          this.error = '';

          // if success we can redirect the user
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.error = error.status + ' ' + error.error.error + ': ' + error.error.error_description;
          this.loading = false;
        });
  }
}
