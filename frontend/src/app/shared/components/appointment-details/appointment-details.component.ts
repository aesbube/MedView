import {Component, Input} from '@angular/core';
import {Appointment} from '../../../models/appointment.model';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {TranslatePipe} from '../../../core/pipes/translate.pipe';

@Component({
  selector: 'app-appointment-details',
  imports: [
    RouterLink,
    MatIconModule,
    TranslatePipe
  ],
  templateUrl: './appointment-details.component.html',
  styleUrl: './appointment-details.component.css'
})
export class AppointmentDetailsComponent {
  @Input() appointment!: Appointment
  @Input() isDoctor: boolean = false;
  @Input() isPatient: boolean = false;
}
