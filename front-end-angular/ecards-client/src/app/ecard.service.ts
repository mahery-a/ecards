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
export class EcardService {

  private handleError: HandleError;
  url = environment.apiUrl + '/api';

  constructor(
    private http: HttpClient,
    httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('EcardService');
  }

  /** POST: sendEcard */
  sendEcard (ecard: Ecard): Observable<Ecard> {
    const ecardData = {'destinationEmail': ecard.emailDestination,
                       'title': ecard.title,
                       'mediaUrl': ecard.mediaUrl,
                       'text': ecard.body};
                       console.log('sending ecard... ');
    return this.http.post<any>(this.url + '/send-ecard', ecardData, httpOptions)
      .pipe(
        catchError(this.handleError('sendEcard', ecard))
      );
  }
}
