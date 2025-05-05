import { Component } from '@angular/core';
import { SearchesComponent } from '../searches/searches.component';
import { MapComponent } from '../map/map.component';

@Component({
  selector: 'app-home',
  imports: [SearchesComponent, MapComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {}
