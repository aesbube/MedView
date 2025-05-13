import { Component, OnInit } from '@angular/core';
import { Prescription } from '../../../../models/prescription.model';
import { Subscription } from 'rxjs';
import { PatientService } from '../../patient.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PrescriptionDetailsComponent } from '../../../../shared/components/prescription-details/prescription-details.component';
import { MatButtonModule } from '@angular/material/button';
import { PrescriptionStatusModel } from '../../../../models/prescription-status.model';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { QrCodeComponent } from "../../../../shared/components/qr-code/qr-code.component";

@Component({
  selector: 'app-patient-prescriptions',
  imports: [
    MatProgressSpinnerModule,
    MatButtonModule,
    MatTableModule,
    RouterLink,
],
  templateUrl: './patient-prescriptions.component.html',
  styleUrl: './patient-prescriptions.component.css',
})
export class PatientPrescriptionsComponent implements OnInit {


  columns = [
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
        columnDef: 'action',
        header: 'Action',
        cell: (precription: Prescription) => `${precription.id}-${precription.patientId}`,
      },
    ];

    displayedColumns = this.columns.map(c => c.columnDef);


  prescriptions: Prescription[] = [];
  numOfPrescriptions = 0;
  fetched = false;
  subscription: Subscription | undefined;
  prescriptionStatus = PrescriptionStatusModel;


  constructor(private patientService: PatientService) {}

  ngOnInit() {
    this.subscription = this.patientService.getPrescriptions().subscribe({
      next: (data: Prescription[]) => {
        this.prescriptions = data;
        this.numOfPrescriptions = this.prescriptions.length;
        this.fetched = true;
      },
      error: (error) => {
        console.error('Error fetching patient data:', error);
      },
      complete: () => {
        console.log('Patient data fetching complete.');
      },
    });
  }


}
