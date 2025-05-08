import { Component } from '@angular/core';
import {AppointmentFreeComponent} from '../appointment-free/appointment-free.component';

@Component({
  standalone: true,
  selector: 'app-specialist-dashboard',
  imports: [AppointmentComponent],
  templateUrl: './specialist-dashboard.component.html',
  styleUrl: './specialist-dashboard.component.css'
})
export class SpecialistDashboardComponent {

}
