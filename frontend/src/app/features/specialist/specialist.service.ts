import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Appointment} from '../../models/appointment.model';
import {FreeAppointment} from '../../models/free-appointment.model';
import {Specialist} from '../../models/specialist.model';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {

  private baseUrl = `${environment.apiUrl}/specialists`;

  constructor(private http: HttpClient) {
  }

  getSavedFreeAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/appointments`);
  }

  setFreeAppointments(appointments: FreeAppointment[]): Observable<string> {
    return this.http.post(`${this.baseUrl}/appointments/set`, appointments, {
      responseType: 'text' as const
    });
  }

  getOccupiedAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseUrl}/occupied-appointments`);
  }

  getSpecialistDetails(): Observable<Specialist> {
    return this.http.get<Specialist>(`${this.baseUrl}/me`);
  }

  updateSpecialistDetails(specialist: Specialist): Observable<string> {
    return this.http.post(`${this.baseUrl}/update`, specialist, {
      responseType: 'text' as const
    });
  }
}
