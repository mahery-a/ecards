import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HttpComponent } from './http.component';
import { AuthGuardService } from '../auth-guard.service';

const routes: Routes = [
  {
    path: 'http', component: HttpComponent,
    // canActivate: [AuthGuardService], // UNCOMMENT THIS TO GUARD THIS PAGE
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HttpRoutingModule { }
