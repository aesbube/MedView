import { Component } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-searches',
  imports: [MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatIconModule],
  templateUrl: './searches.component.html',
  styleUrl: './searches.component.css',
})
export class SearchesComponent {

  specialistOrEquipment = '';
  appointment = '';
}
