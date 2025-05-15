import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { PatientService } from '../../patient.service';
import { Prescription } from '../../../../models/prescription.model';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {PrescriptionStatusModel} from '../../../../models/prescription-status.model';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { Appointment } from '../../../../models/appointment.model';

@Component({
  selector: 'app-patient-history',
  imports: [
        MatProgressSpinnerModule,
    MatButtonModule,
    MatTableModule,
    RouterLink,
  ],
  templateUrl: './patient-history.component.html',
  styleUrl: './patient-history.component.css'
})
export class PatientHistoryComponent {

 precriptionColumns = [
      {
        columnDef: 'doctor',
        header: 'Doctor',
        cell: (precription: Prescription) => `${precription.doctorNameAndSurname}`,
      },
      {
        columnDef: 'medicine',
        header: 'Medicine',
        cell: (precription: Prescription) => `${precription.medicine}`,
      },
      {
        columnDef: 'frequency',
        header: 'Frequency',
        cell: (precription: Prescription) => `${precription.frequency}`,
      },
      {
        columnDef: 'expiration-date',
        header: 'Expiration date',
        cell: (precription: Prescription) => `${precription.expirationDate}`,
      },
      {
        columnDef: 'status',
        header: 'Status',
        cell: (precription: Prescription) => `${precription.status}`,
      },
    ];

    displayedPrecriptionColumns = this.precriptionColumns.map(c => c.columnDef);


  prescriptions: Prescription[] = [];
  numOfPrescriptions = 0;
  fetchedPrescriptions = false;
  subscription: Subscription | undefined;
  prescriptionStatus = PrescriptionStatusModel;


  constructor(private patientService: PatientService) {}

  ngOnInit() {
    this.subscription = this.patientService.getPrescriptions().subscribe({
      next: (data: Prescription[]) => {
        this.prescriptions = data;
        this.numOfPrescriptions = this.prescriptions.length;
        this.fetchedPrescriptions = true;
      },
      error: (error) => {
        console.error('Error fetching patient data:', error);
      },
      complete: () => {
        console.log('Patient data fetching complete.');
      },
    });

    this.subscription = this.patientService.getAppointments().subscribe({
        next: (data: Appointment[]) => {
          this.appointments = data;
          this.fetchedAppointments = true;
        },
        error: (error) => {
          console.error('Error fetching patient data:', error);
        },
        complete: () => {
          console.log('Patient data fetching complete.');
        }
      });
  }





  appointmentColumns = [
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

    displayedAppointmentColumns = this.appointmentColumns.map(c => c.columnDef);

    appointments: Appointment[] = []
    fetchedAppointments = false
    today = this.getDate()


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
