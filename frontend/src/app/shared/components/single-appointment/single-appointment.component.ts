import { Component, OnInit } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment } from '../../../models/appointment.model';
import { ActivatedRoute } from '@angular/router';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-single-appointment',
  imports: [MatDividerModule, MatProgressSpinnerModule],
  templateUrl: './single-appointment.component.html',
  styleUrl: './single-appointment.component.css',
})
export class SingleAppointmentComponent {
  private apiUrl = environment.apiUrl;
  appointmentId!: string;
  appointment: Appointment | null = null;
  fetched = false;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit() {
    this.appointmentId = this.route.snapshot.params['id'];
    console.log('Helelo');

    this.getAppointment().subscribe({
      next: (appointment) => {
        this.appointment = appointment;
        this.fetched = true;
      },
      error: (error) => {
        console.error('Error loading appointment:', error);
      },
    });
  }

  getAppointment(): Observable<Appointment> {
    return this.http.get<Appointment>(
      `${this.apiUrl}/appointments/${this.appointmentId}`
    );
  }
}
