import { Component } from '@angular/core';
import { UploaderService } from './uploader.service';

@Component({
  selector: 'app-uploader',
  template: `
  <h3>Upload file</h3>
<form enctype="multipart/form-data" method="post">
  <div>
    <label for="picked">Choose file to upload</label>
    <div>
      <input type="file" id="picked" #picked
        (click)="message=''"
        (change)="onPicked(picked)">
    </div>
  </div>
  <p *ngIf="message">{{message}}</p>
</form>
  `,
  providers: [ UploaderService ]
})
export class UploaderComponent {
  message: string;

  constructor(private uploaderService: UploaderService) {}

  onPicked(input: HTMLInputElement) {
    const file = input.files[0];
    if (file) {
      this.uploaderService.upload(file).subscribe(
        msg => {
          input.value = null;
          this.message = msg;
        }
      );
    }
  }
}
