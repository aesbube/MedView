import { Component } from '@angular/core';
import { SideMenuComponent } from "../side-menu/side-menu.component";
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-patient-dashboard',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    SideMenuComponent,],
  templateUrl: './patient-dashboard.component.html',
  styleUrl: './patient-dashboard.component.css'
})
export class PatientDashboardComponent {

}
