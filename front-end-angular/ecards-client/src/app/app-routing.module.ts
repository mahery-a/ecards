import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { FullComponent } from './layouts/full/full.component';
import { BlankComponent } from './layouts/blank/blank.component';
import { AuthGuardService } from './auth-guard.service';

export const Approutes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      { path: '', redirectTo: '/gallery/happy+birthday', pathMatch: 'full' },
      {
        path: 'starter',
        loadChildren: './starter/starter.module#StarterModule'
      },
      {
        path: 'gallery/:theme',
        loadChildren: './gallery/gallery.module#GalleryModule',
        canLoad: [AuthGuardService]
      },
      {
        path: 'ecard-editor',
        loadChildren: './ecard-editor/ecard-editor.module#EcardEditorModule',
        canLoad: [AuthGuardService]
      }
    ]
  },
  {
    path: '',
    component: BlankComponent,
    children: [
      {
        path: 'authentication',
        loadChildren:
          './authentication/authentication.module#AuthenticationModule'
      }
    ]
  },
  {
    path: '**',
    redirectTo: '/authentication/404'
  }
];
