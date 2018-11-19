import { HttpRoutingModule } from './http-routing.module';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './in-memory-data.service';
import { HeroesComponent } from './heroes/heroes.component';
import { HttpComponent } from './http.component';
import { FormsModule } from '@angular/forms';
import { HttpErrorHandler } from './http-error-handler.service';
import { CommonModule } from '@angular/common';
import { MessageService } from './message.service';
import { DownloaderComponent } from './downloader/downloader.component';
import { UploaderComponent } from './uploader/uploader.component';
import { PackageSearchComponent } from './package-search/package-search.component';
import { httpInterceptorProviders } from './http-interceptors';
import { RequestCacheWithMap, RequestCache } from './request-cache.service';


@NgModule({
  imports: [
    // import HttpClientModule after BrowserModule.
    CommonModule,
    FormsModule,
    HttpRoutingModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'My-Xsrf-Cookie',
      headerName: 'My-Xsrf-Header',
    }),

    // BELOW IS USEFUL FOR TESTING PURPOSE: IN MEMORY DB !!!!
    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, {
        dataEncapsulation: false,
        passThruUnknownUrl: true,
        put204: false // return entity after PUT/update
      }
    )
  ],
  declarations: [
    HttpComponent,
    HeroesComponent,
    DownloaderComponent,
    UploaderComponent,
    PackageSearchComponent,
    /*ConfigComponent,
    MessagesComponent,*/
  ],
  providers: [
    HttpErrorHandler,
    MessageService,
    HttpErrorHandler,
    { provide: RequestCache, useClass: RequestCacheWithMap },
    httpInterceptorProviders
  ]
})
export class HttpModule {}
