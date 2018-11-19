import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/authentication-oauth2.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html'
})
export class SignupComponent {

  loading = false;
  error = '';
  success = '';

  constructor(private route: ActivatedRoute,
    private router: Router,
    public authenticationService: AuthenticationService) {}

  onSubmit(username: string, email: string, password: string, confirmPassword: string) {
    event.preventDefault();
    console.log('onSubmit ' + username);

    // stop here if form is invalid
    if (username === '' || password === '' || email === '' || (password !== confirmPassword)) {
      this.error = 'fields are invalid';
      return;
    }

    console.log('form is valid');

    this.loading = true;
    this.authenticationService.signup(username, email, password)
      .pipe(first())
      .subscribe(
        data => {
          // success: we've received the token
          // console.log('TOKEN:' + data.access_token);
          this.error = '';
          this.loading = false;

          // if success we can redirect the user
          // this.router.navigate([this.returnUrl]);
          this.success = 'Success! Try to login with your credentials now.';
        },
        error => {
          this.error = error.status + ' ' + error.error.error + ': ' + error.error.error_description;
          this.loading = false;
        });
  }

}
