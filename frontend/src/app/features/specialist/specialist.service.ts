import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from '../../models/appointment.model';
import { FreeAppointment } from '../../models/free-appointment.model';

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {

  private baseUrl = 'http://localhost:8080/specialists/appointments';

  constructor(private http: HttpClient) { }

  getOccupiedAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}`);
  }

  setFreeAppointments(appointments: FreeAppointment[]): Observable<any> {
    return this.http.post(`${this.baseUrl}/set`, appointments);
  }
}
