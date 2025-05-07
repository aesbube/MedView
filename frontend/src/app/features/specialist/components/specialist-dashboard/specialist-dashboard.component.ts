import { Component } from '@angular/core';
import {AppointmentComponent} from '../../../../shared/components/appointment/appointment.component';

@Component({
  standalone: true,
  selector: 'app-specialist-dashboard',
  imports: [AppointmentComponent],
  templateUrl: './specialist-dashboard.component.html',
  styleUrl: './specialist-dashboard.component.css'
})
export class SpecialistDashboardComponent {

}
