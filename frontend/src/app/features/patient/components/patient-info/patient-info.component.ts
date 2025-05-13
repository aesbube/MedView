import { Component, inject, OnInit } from '@angular/core';
import { PatientService } from '../../patient.service';
import { Patient } from '../../../../models/patient.model';
import { Subscription } from 'rxjs';
import { Doctor } from '../../../../models/doctor.model';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {
  FormControl,
  Validators,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-patient-info',
  imports: [
    MatProgressSpinnerModule,
    MatDividerModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './patient-info.component.html',
  styleUrl: './patient-info.component.css',
})
export class PatientInfoComponent implements OnInit {
  edit = false;
  patientService = inject(PatientService);
  patient: Patient = {} as Patient;
  subscription: Subscription | undefined;
  fetched = false;
  username = '';
  doctor = {} as Doctor;
  email = '';
  phone = '';

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  phoneFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(9),
  ]);

  constructor() {
    this.phoneFormControl.valueChanges.subscribe((value) =>{
      this.phone = value || ""
    })

    this.emailFormControl.valueChanges.subscribe((value) =>{
      this.email = value || ""
    })
  }

  ngOnInit(): void {
    this.getPatientInfo()
  }

  updatePatientInfo() {
    this.edit=false
    console.log(this.email, this.phone)
    this.patientService.updatePatient(this.patient.username,this.email, this.phone).subscribe({
    next: () => this.getPatientInfo(),
    error: (error) => console.error('Failed to update patient info:', error),
  });
  }

  getPatientInfo(){
    this.subscription = this.patientService.getPatient().subscribe({
      next: (data: Patient) => {
        this.patient = data;
        this.email = this.patient.email;
        this.phone = this.patient.phone;
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
