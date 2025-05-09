import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from '../../models/patient.model';
import { Prescription } from '../../models/prescription.model';
import { WritePrescription } from '../../models/write-prescription';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private baseUrl = 'http://localhost:8080/doctors';
  private patientsUrl = 'http://localhost:8080/patients';

  constructor(private http: HttpClient) {
  }

  getMyPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/patients`);
  }

  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.patientsUrl}`);
  }

  claimPatient(patientUsername: string): Observable<Patient> {
    return this.http.post<Patient>(`${this.baseUrl}/add-patient`, { username: patientUsername });
  }

  getPatientDetails(patientId: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.baseUrl}/patients/${patientId}`);
  }

  getPatientPrescriptions(patientId: number): Observable<Prescription[]> {
    return this.http.get<any[]>(`${this.baseUrl}/patients/${patientId}/prescriptions`);
  }

  writePrescription(patientId: number, prescription: WritePrescription): Observable<WritePrescription> {
    return this.http.post<WritePrescription>(`${this.baseUrl}/patients/${patientId}/prescriptions/new`, prescription);
  }

  cancelPrescription(patientId: number, prescriptionId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/patients/${patientId}/prescriptions/${prescriptionId}`);
  }

}
