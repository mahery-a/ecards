import { environment } from './../environments/environment';
import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { HandleError, HttpErrorHandler } from './http/http-error-handler.service';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Ecard } from './ecard';

/*const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'my-auth-token'
  })
};*/
const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  'Authorization': 'my-auth-token'
});
const params = new HttpParams()
  .set('username', 'test@example.com')
  .set('password', 'secret')
  .set('grant_type', 'password');
const httpOptions = {
  headers,
  params
  // withCredentials: true
};

@Injectable()
export class ThemeService {

  private handleError: HandleError;
  url = environment.apiUrl + '/api';

  constructor(
    private http: HttpClient,
    httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('EcardService');
  }

  /** GET: getThemes */
  getThemes (): Observable<any> {
    console.log('get themes ');
    return this.http.get<any>(this.url + '/theme', httpOptions)
      .pipe(
        catchError(this.handleError('getThemes'))
      );
  }
}
