import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';


import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { HttpErrorHandler, HandleError } from '../http/http-error-handler.service';
import { environment } from 'src/environments/environment';

// can be in a separate file image.ts
export interface Image {
  id: number;
  name: string;
}

/*
export declare class Image {
    id: number;
    largeUrl: string;
    thumbUrl: string;
    size: string;
    width: number;
    height: number;
    description: string;
    author: string;
    constructor();
}
*/

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'my-auth-token',
  })
};

@Injectable()
export class ImagesService {
  // final url example: 'http://api.giphy.com/v1/gifs/search?q=ryan+gosling&api_key=YOUR_API_KEY&limit=5'
  search = 'happy+birthday';
  baseUrl = `http://api.giphy.com/v1/gifs/search?q=${this.search}&api_key=${environment.giphy_api_key}`;
  args = '&limit=3';
  imagesUrl = this.baseUrl + this.args;  // URL to web api
  private handleError: HandleError;

  constructor(
    private http: HttpClient,
    httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('ImagesService');
  }

  /** GET images from the server */
  getImages (theme: string): Observable<any/*Image[]*/> {
    const url = `http://api.giphy.com/v1/gifs/search?q=${theme}&api_key=${environment.giphy_api_key}&limit=500`;
    return this.http.get<any/*Image[]*/>(url)
      .pipe(
        catchError(this.handleError('getImages', []))
      );
  }

  /* GET images whose name contains search term */
  searchImages(term: string): Observable<Image[]> {
    term = term.trim();

    // Add safe, URL encoded search parameter if there is a search term
    const options = term ?
     { params: new HttpParams().set('name', term) } : {};

    return this.http.get<Image[]>(this.imagesUrl, options)
      .pipe(
        catchError(this.handleError<Image[]>('searchImages', []))
      );
  }

  //////// Save methods //////////

  /** POST: add a new image to the database */
  addImage (image: Image): Observable<Image> {
    return this.http.post<Image>(this.imagesUrl, image, httpOptions)
      .pipe(
        catchError(this.handleError('addImage', image))
      );
  }

  /** DELETE: delete the image from the server */
  deleteImage (id: number): Observable<{}> {
    const url = `${this.imagesUrl}/${id}`; // DELETE api/heroes/42
    return this.http.delete(url, httpOptions)
      .pipe(
        catchError(this.handleError('deleteImage'))
      );
  }

  /** PUT: update the image on the server. Returns the updated image upon success. */
  updateImage (image: Image): Observable<Image> {
    httpOptions.headers =
      httpOptions.headers.set('Authorization', 'my-new-auth-token');

    return this.http.put<Image>(this.imagesUrl, image, httpOptions)
      .pipe(
        catchError(this.handleError('updateImage', image))
      );
  }
}
