import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { NpmPackageInfo, PackageSearchService } from './package-search.service';

@Component({
  selector: 'app-package-search',
  template: `
  <h3>Search Npm Packages</h3>
<p><i>Searches when typing stops. Caches for 30 seconds.</i></p>
<input (keyup)="search($event.target.value)" id="name" placeholder="Search"/>
<input type="checkbox" id="refresh" [checked]="withRefresh" (click)="toggleRefresh()">
<label for="refresh">with refresh</label>

<ul>
  <li *ngFor="let package of packages$ | async">
    <b>{{package.name}} v.{{package.version}}</b> -
    <i>{{package.description}}</i>
  </li>
</ul>
`,
  providers: [ PackageSearchService ]
})
export class PackageSearchComponent implements OnInit {
  withRefresh = false;
  packages$: Observable<NpmPackageInfo[]>;
  private searchText$ = new Subject<string>();

  search(packageName: string) {
    this.searchText$.next(packageName);
  }

  ngOnInit() {
    this.packages$ = this.searchText$.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap(packageName =>
        this.searchService.search(packageName, this.withRefresh))
    );
  }

  constructor(private searchService: PackageSearchService) { }


  toggleRefresh() { this.withRefresh = ! this.withRefresh; }

}
