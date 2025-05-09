import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Patient} from '../../models/patient.model';
import {Prescription} from '../../models/prescription.model';
import {WritePrescription} from '../../models/write-prescription';
import {environment} from '../../../environments/environment';
import {Doctor} from '../../models/doctor.model';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
  private baseUrl = `${environment.apiUrl}/doctors`;
  private patientsUrl = `${environment.apiUrl}/patients`;

  constructor(private http: HttpClient) {
  }

  getMyPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/patients`);
  }

  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.patientsUrl}`);
  }

  claimPatient(patientUsername: string): Observable<Patient> {
    return this.http.post<Patient>(`${this.baseUrl}/add-patient`, {username: patientUsername});
  }

  getPatientDetails(patientId: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.baseUrl}/patients/${patientId}`);
  }

  getDoctorDetails(): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseUrl}/me`);
  }

  updateDoctorDetails(doctor: Doctor): Observable<string> {
    return this.http.post(`${this.baseUrl}/update`, doctor, {
      responseType: 'text' as const
    });
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
