import { Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import { TranslatePipe } from '../../../core/pipes/translate.pipe';

@Component({
  selector: 'app-map-search',
  imports: [MatButtonModule, TranslatePipe],
  templateUrl: './map-search.component.html',
  styleUrl: './map-search.component.css'
})
export class MapSearchComponent {

}
