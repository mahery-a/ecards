import { DataService } from 'src/app/data.service';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Image } from 'angular2_photoswipe';
import { ImagesService } from '../images.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styles: [],
  providers: [ ImagesService ]
})
export class GalleryComponent implements OnInit {

  image1: Image;
  image2: Image;

  /*constructor() {
  }

  ngOnInit() {
    console.log('baseUrl: ' + this.baseUrl);
    console.log('finalUrl: ' + this.finalUrl);
    const height = 3296, width = 4934;

    this.image1 = new Image();
    this.image1.largeUrl = 'https://picsum.photos/4934/3296/?image=1';
    this.image1.height = height;
    this.image1.width = width;
    this.image1.id = 0;
    this.image1.size = `${this.image1.width}x${this.image1.height}`;
    this.image1.thumbUrl = 'https://picsum.photos/300/200/?image=1';

    this.image2 = new Image();
    this.image2.largeUrl = 'https://picsum.photos/4934/3296/?image=2';
    this.image2.height = height;
    this.image2.width = width;
    this.image2.id = 1;
    this.image2.size = `${this.image2.width}x${this.image2.height}`;
    this.image2.thumbUrl = 'https://picsum.photos/300/200/?image=2';
  }*/

  images: Image[] = Array();
  total_count: Number = 0;
  response: any;
  @Output() pageChange: EventEmitter<number>;
  itemsPerPage = 20;
  currentPage = 1;
  // editHero: Hero; // the hero currently being edited

  constructor(private imagesService: ImagesService,
              private route: ActivatedRoute,
              private router: Router,
              private dataService: DataService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.currentPage = 1;
      this.getImages(params.get('theme'));
    });
  }

  onThumbnailClick(indexOnPage) {
    this.dataService.setCurrentImage(this.images[this.getAbsoluteImageIndex(indexOnPage)].largeUrl);
  }

  getImage(indexOnPage) {
    return this.images[this.getAbsoluteImageIndex(indexOnPage)];
  }

  getAbsoluteImageIndex(indexOnPage) {
    return this.itemsPerPage * (this.currentPage - 1) + indexOnPage;
  }

  getImages(theme: string): void {
    this.images.length = 0; // clear the array
    this.imagesService.getImages(theme)
    /*.map(res => res.json())*/.subscribe(data => {
      this.response = data;
      // console.log('RESPONSE:' + JSON.stringify(data));
      this.total_count = this.response.pagination.count;
      console.log('COUNT: ' + this.total_count);
      // TODO: init the aray images: Image[]; with total_count
      for (let i = 0; i < this.response.data.length; i++) {
         const image = new Image();
         // for details https://developers.giphy.com/docs/#rendition-guide
         image.largeUrl = this.response.data[i].images.original.url;
         image.width = this.response.data[i].images.fixed_height_downsampled.width;
         image.height = this.response.data[i].images.fixed_height_downsampled.height;
         image.size = this.response.data[i].images.fixed_height_downsampled.size;
         image.thumbUrl = this.response.data[i].images.fixed_height_downsampled.url;
         this.images.push(image);
      }
    });
    // }
      // .subscribe(/*images*/ response => {
      //  this.images = images;
      // });
  }

  /*add(name: string): void {
    this.editHero = undefined;
    name = name.trim();
    if (!name) { return; }

    // The server will generate the id for this new hero
    const newHero: Hero = { name } as Hero;
    this.heroesService.addHero(newHero)
      .subscribe(hero => this.heroes.push(hero));
  }

  delete(hero: Hero): void {
    this.heroes = this.heroes.filter(h => h !== hero);
    this.heroesService.deleteHero(hero.id).subscribe();
    // oops ... subscribe() is missing so nothing happens
    // this.heroesService.deleteHero(hero.id);
  }

  edit(hero) {
    this.editHero = hero;
  }

  search(searchTerm: string) {
    this.editHero = undefined;
    if (searchTerm) {
      this.heroesService.searchHeroes(searchTerm)
        .subscribe(heroes => this.heroes = heroes);
    }
  }

  update() {
    if (this.editHero) {
      this.heroesService.updateHero(this.editHero)
        .subscribe(hero => {
          // replace the hero in the heroes list with update from server
          const ix = hero ? this.heroes.findIndex(h => h.id === hero.id) : -1;
          if (ix > -1) { this.heroes[ix] = hero; }
        });
      this.editHero = undefined;
    }
  }*/

}
