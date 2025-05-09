import { Component } from '@angular/core';
import { AppointmentDetailsComponent } from "../../../../shared/components/appointment-details/appointment-details.component";
import { PatientService } from '../../patient.service';
import { Appointment } from '../../../../models/appointment.model';
import { Subscription } from 'rxjs';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';


@Component({
  selector: 'app-patient-appointments',
  imports: [
    AppointmentDetailsComponent,
    MatProgressSpinnerModule],
  templateUrl: './patient-appointments.component.html',
  styleUrl: './patient-appointments.component.css'
})
export class PatientAppointmentsComponent {

  appointments : Appointment[] = [{} as Appointment, {} as Appointment,{} as Appointment,]
  numOfAppointments = 0
  fetched = false
  subscription: Subscription | undefined;


  constructor (private patientService: PatientService) {}

  ngOnInit(){
    this.subscription = this.patientService.getAppointments().subscribe({
          next: (data: Appointment[]) => {
            this.appointments = data;
            this.numOfAppointments = this.appointments.length
            this.fetched = true;
          },
          error: (error) => {
            console.error('Error fetching patient data:', error);
          },
          complete: () => {
            console.log('Patient data fetching complete.');
          }
        });
  }

}
