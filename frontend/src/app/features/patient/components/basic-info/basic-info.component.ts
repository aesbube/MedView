import { Component, inject, OnInit } from '@angular/core';
import { PatientService } from '../../patient.service';
import { Patient } from '../../../../models/patient.model';
import { Observable, of, Subscription, tap } from 'rxjs';
import { Doctor } from '../../../../models/doctor.model';

@Component({
  selector: 'app-basic-info',
  imports: [],
  templateUrl: './basic-info.component.html',
  styleUrl: './basic-info.component.css'
})
export class BasicInfoComponent implements OnInit{

  patientService = inject(PatientService)
  patient : Patient = {} as Patient
  subscription: Subscription | undefined;

  username = ""
  email = ""
  doctor = {} as Doctor

  ngOnInit(): void {
    this.subscription = this.patientService.getPatient().subscribe({
      next: (data: Patient) => {
        this.patient = data;
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
