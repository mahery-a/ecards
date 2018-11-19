import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  currentImage: string;

  getCurrentImage() {
    return this.currentImage;
  }

  setCurrentImage(image: string) {
    this.currentImage = image;
  }
}
