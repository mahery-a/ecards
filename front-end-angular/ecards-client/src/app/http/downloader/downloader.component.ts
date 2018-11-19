import { Component } from '@angular/core';
import { DownloaderService } from './downloader.service';

@Component({
  selector: 'app-downloader',
  template: `
  <h3>Download the textfile</h3>
<button (click)="download()">Download</button>
<button (click)="clear()">clear</button>
<p *ngIf="contents">Contents: "{{contents}}"</p>
  `,
  providers: [ DownloaderService ]
})
export class DownloaderComponent {
  contents: string;
  constructor(private downloaderService: DownloaderService) {}

  clear() {
    this.contents = undefined;
  }

  download() {
    this.downloaderService.getTextFile('assets/textfile.txt')
      .subscribe(results => this.contents = results);
  }
}
