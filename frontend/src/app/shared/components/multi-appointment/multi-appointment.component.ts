import { Component, Input } from '@angular/core';
import { Appointment } from '../../../models/appointment.model';

@Component({
  selector: 'app-multi-appointment',
  imports: [],
  templateUrl: './multi-appointment.component.html',
  styleUrl: './multi-appointment.component.css'
})
export class MultiAppointmentComponent {
  @Input() appointment!: Appointment
}
