import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/data.service';
import { EcardService } from 'src/app/ecard.service';
import { Ecard } from 'src/app/ecard';

@Component({
  selector: 'app-ecard-editor',
  templateUrl: './ecard-editor.component.html',
  styleUrls: ['./ecard-editor.component.css'],
  providers: [EcardService]
})
export class EcardEditorComponent implements OnInit {

  imagePath: string;
  textbody;
  emailField;
  title;

  constructor(private ecardService: EcardService,
              private dataservice: DataService) {
    this.imagePath = dataservice.getCurrentImage();
  }

  ngOnInit() {
  }

  onSubmit() {
    console.log('title: ' + this.title);
    console.log('TEXT: ' + this.textbody);
    console.log('email: ' + this.emailField);
    const ecard = new Ecard();
    ecard.title = this.title;
    ecard.body = this.textbody;
    ecard.emailDestination = this.emailField;
    ecard.mediaUrl = this.imagePath;
    this.ecardService.sendEcard(ecard).subscribe(
      response => console.log(response),
      err => console.log(err)
    );
  }

}
