import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Appointment} from '../../models/appointment.model';
import {FreeAppointment} from '../../models/free-appointment.model';
import {Specialist} from '../../models/equipment.model';

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {

  private baseUrl = 'http://localhost:8080/specialists';

  constructor(private http: HttpClient) {
  }

  getSavedFreeAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointments`);
  }

  setFreeAppointments(appointments: FreeAppointment[]): Observable<any> {
    return this.http.post(`${this.baseUrl}/appointments/set`, appointments);
  }

  getOccupiedAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/occupied-appointments`);
  }

  getSpecialistDetails(): Observable<Specialist> {
    return this.http.get<Specialist>(`${this.baseUrl}/me`);
  }

  updateSpecialistDetails(specialist: Specialist): Observable<Specialist> {
    return this.http.post<Specialist>(`${this.baseUrl}/update`, specialist);
  }
}
