import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  // store the URL so we can redirect after logging in
  redirectUrl: string;

  getAuthorizationToken(): any {
    return this.cookieService.get('access_token');
  }

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  login(username: string, password: string) {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    params.append('grant_type', 'password');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
        'Authorization': 'Basic ' + btoa('trusted-app:secret')
      })
    };
    console.log('PARAMS:' + params.toString());
    return this.http.post<any>(environment.apiUrl + `/oauth/token`, params.toString(), httpOptions)// { username, password })
      .pipe(map(data => {
        // console.log('test' + JSON.stringify(data));
        // login successful if there's a jwt token in the response
        if (data && data.access_token) {
          // success: we've received the token
          // console.log('TOKEN:' + data.access_token);
          console.log(data);
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          // localStorage.setItem('currentUser', JSON.stringify(data));

          // cookie alternative
          const expireDate = new Date().getTime() + (1000 * data.expires_in);
          this.cookieService.set('access_token', data.access_token, expireDate);
        }
        return data;
      }));
  }

  signup(username: string, email: string, password: string) {
    const credentials = {'username': username,
                       'email': email,
                       'password': password};
                       const headers = new HttpHeaders({
                        'Content-Type': 'application/json',
                        'Authorization': 'my-auth-token'
                      });
                      const params = new HttpParams()
                        .set('username', 'test@example.com')
                        .set('password', 'secret')
                        .set('grant_type', 'password');
                      const httpOptions2 = {
                        headers,
                        params
                        // withCredentials: true
                      };

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-type': 'application/json'
      })
    };
    console.log('data:' + credentials);
    return this.http.post<any>(environment.apiUrl + `/api/user/register`, credentials, httpOptions2)// { username, password })
      .pipe(map(data => {
        console.log(data);
        return data;
      }));
  }

  logout() {
    // remove user from local storage to log user out
    // localStorage.removeItem('currentUser');

    // cookie alternative
    const cookieExists: boolean = this.cookieService.check('access_token');
    if (cookieExists) {
      console.log('deleting cookie...');
      this.cookieService.delete('access_token');
    }
  }

  isLoggedIn() {
    const cookieExists: boolean = this.cookieService.check('access_token');
    return cookieExists;
  }
}
