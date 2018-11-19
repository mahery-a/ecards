import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private cookieService: CookieService) { }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    // const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    /*if (currentUser && currentUser.token) {
      request = request.clone({
          setHeaders: {
              Authorization: `Bearer ${currentUser.token}`
          }
      });
    }*/
    // cookie alternative
    if (request.url.lastIndexOf(environment.apiUrl, 0) === 0) {  // only add the header when requesting to the spring API
      const cookieExists: boolean = this.cookieService.check('access_token');
      if (cookieExists) {
        request = request.clone({
          setHeaders: {
            Authorization: `Bearer ` + this.cookieService.get('access_token')
          }
        });
      }
    }

    return next.handle(request);
  }
}
