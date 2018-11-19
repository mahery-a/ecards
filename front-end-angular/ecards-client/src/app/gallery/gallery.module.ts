import { GalleryRoutes } from './gallery.routing';
import { NgModule, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GalleryComponent } from './gallery/gallery.component';
import { RouterModule } from '@angular/router';
import {Angular2PhotoswipeModule} from 'angular2_photoswipe';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  imports: [
    CommonModule,
    Angular2PhotoswipeModule,
    NgxPaginationModule,
    RouterModule.forChild(GalleryRoutes)
  ],
  providers: [],
  declarations: [GalleryComponent]
})
export class GalleryModule implements OnInit {
  ngOnInit(): void {
  }
}
