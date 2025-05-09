import { Component, inject, OnInit } from '@angular/core';
import { PatientService } from '../../patient.service';
import { Patient } from '../../../../models/patient.model';
import { Subscription } from 'rxjs';
import { Doctor } from '../../../../models/doctor.model';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@Component({
  selector: 'app-patient-info',
  imports: [MatProgressSpinnerModule],
  templateUrl: './patient-info.component.html',
  styleUrl: './patient-info.component.css'
})
export class PatientInfoComponent implements OnInit{

  patientService = inject(PatientService)
  patient : Patient = {} as Patient
  subscription: Subscription | undefined;
  fetched = false;
  username = ""
  email = ""
  doctor = {} as Doctor

  ngOnInit(): void {
    this.subscription = this.patientService.getPatient().subscribe({
      next: (data: Patient) => {
        this.patient = data;
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
