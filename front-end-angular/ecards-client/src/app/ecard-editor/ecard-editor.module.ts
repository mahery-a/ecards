import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EcardEditorComponent } from './ecard-editor/ecard-editor.component';
import { QuillModule } from 'ngx-quill';
import { RouterModule } from '@angular/router';
import { EcardEditorRoutes } from './ecard-editor.routing';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    QuillModule,
    FormsModule,
    RouterModule.forChild(EcardEditorRoutes)
  ],
  declarations: [EcardEditorComponent]
})
export class EcardEditorModule { }
