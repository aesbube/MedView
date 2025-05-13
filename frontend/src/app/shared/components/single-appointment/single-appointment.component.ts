import { Component } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment } from '../../../models/appointment.model';

@Component({
  selector: 'app-single-appointment',
  imports: [],
  templateUrl: './single-appointment.component.html',
  styleUrl: './single-appointment.component.css'
})
export class SingleAppointmentComponent {

  private apiUrl = environment.apiUrl;

    constructor(private http: HttpClient) { }

     getAppointment(): Observable<Appointment>{
        return this.http.get<Appointment>(`${this.apiUrl}/patients/appointments`);
      }

}
