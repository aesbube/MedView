import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Patient } from '../../models/patient.model';
import { Observable } from 'rxjs';
import { Appointment } from '../../models/appointment.model';
import { Prescription } from '../../models/prescription.model';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getPatient(): Observable<Patient> {
    return this.http.get<Patient>(`${this.apiUrl}/patients/me`);
  }

  getAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/patients/appointments`);
  }

  getPrescriptions(): Observable<Prescription[]> {
    return this.http.get<Prescription[]>(
      `${this.apiUrl}/patients/me/prescriptions`
    );
  }

  updatePatient(username: string, email: string, phone: string) {
    return this.http.post<Patient>(`${this.apiUrl}/patients/update`, {
      username: username,
      email: email,
      phone: phone,
    });
  }
}
