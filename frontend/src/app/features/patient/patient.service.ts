import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Patient } from '../../models/patient.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class PatientService{

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getPatient(): Observable<Patient> {
    return this.http.get<Patient>(`${this.apiUrl}/patients/me`);
  }


}
