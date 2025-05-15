import { Component } from '@angular/core';
import { PatientService } from '../../patient.service';
import { Appointment } from '../../../../models/appointment.model';
import { Subscription } from 'rxjs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {MatTableModule} from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-patient-appointments',
  imports: [
    MatTableModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    RouterLink
],
  templateUrl: './patient-appointments.component.html',
  styleUrl: './patient-appointments.component.css'
})
export class PatientAppointmentsComponent {

  columns = [
    {
      columnDef: 'position',
      header: 'Date/Time',
      cell: (appointment: Appointment) => `${appointment.date} at ${appointment.time}`,
    },
    {
      columnDef: 'name',
      header: 'Name',
      cell: (appointment: Appointment) => `${appointment.patient?.name} ${appointment.patient?.surname}`,
    },
    {
      columnDef: 'specialist',
      header: 'Specialist',
      cell: (appointment: Appointment) => `${appointment.schedule.specialist.name} ${appointment.schedule.specialist.surname}`,
    },
    {
      columnDef: 'doctor',
      header: 'Doctor',
      cell: (appointment: Appointment) => `${appointment.assignee?.name} ${appointment.assignee?.surname}`,
    },
    {
      columnDef: 'location',
      header: 'Location',
      cell: (appointment: Appointment) => `${appointment.location}`,
    },
    {
      columnDef: 'details',
      header: 'Details',
      cell: (appointment: Appointment) => `${appointment.refNumber}`,
    },
  ];

  displayedColumns = this.columns.map(c => c.columnDef);

  appointments: Appointment[] = []
  fetched = false
  subscription: Subscription | undefined;
  today = this.getDate()

  constructor(private patientService: PatientService) { }

  ngOnInit() {
    console.log(this.today);

    this.subscription = this.patientService.getAppointments().subscribe({
      next: (data: Appointment[]) => {
        this.appointments = data;
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

  getDate() {
    var d = new Date(Date.now()),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

}
