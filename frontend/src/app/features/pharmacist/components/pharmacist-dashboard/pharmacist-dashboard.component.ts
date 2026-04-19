import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {TranslatePipe} from '../../../../core/pipes/translate.pipe';

@Component({
  selector: 'app-pharmacist-dashboard',
  imports: [RouterLink, MatIconModule, TranslatePipe],
  templateUrl: './pharmacist-dashboard.component.html',
  styleUrl: './pharmacist-dashboard.component.css'
})
export class PharmacistDashboardComponent {}
