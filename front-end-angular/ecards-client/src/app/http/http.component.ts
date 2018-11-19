import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-http',
  template: `
  <app-heroes></app-heroes>
  <app-downloader></app-downloader>
  <app-uploader></app-uploader>
  <app-package-search></app-package-search>
  `,
  /*<app-messages></app-messages>
  */
})
export class HttpComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
