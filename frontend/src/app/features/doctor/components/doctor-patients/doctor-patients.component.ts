import { Component, inject, OnInit } from '@angular/core';
import { DoctorService } from '../../doctor.service';
import { Patient } from '../../../../models/patient.model';

@Component({
  selector: 'app-doctor-patients',
  imports: [],
  templateUrl: './doctor-patients.component.html',
  styleUrl: './doctor-patients.component.css'
})
export class DoctorPatientsComponent implements OnInit {
  service = inject(DoctorService);
  patients: Patient[] = []

  ngOnInit(): void {
    this.loadMyPatients()
  }

  loadMyPatients() {
    this.service.getMyPatients().subscribe({
      next: (patients) => {
        this.patients = patients;
        console.log('Patients loaded:', this.patients);
      },
      error: (error) => {
        console.error('Error loading patients:', error);
      }
    });
  }
}
