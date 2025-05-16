import { Component, OnInit } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment } from '../../../models/appointment.model';
import { ActivatedRoute } from '@angular/router';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Diagnosis } from '../../../models/diagnosis.model';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-single-appointment',
  imports: [MatDividerModule, MatProgressSpinnerModule, MatButtonModule],
  templateUrl: './single-appointment.component.html',
  styleUrl: './single-appointment.component.css',
})
export class SingleAppointmentComponent {
  private apiUrl = environment.apiUrl;
  appointmentRef!: string;
  appointment: Appointment | null = null;
  diagnosis: Diagnosis | null = null;
  fetchedAppointment = false;
  fetchedDiagnosis = false;
  simplifying = false;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit() {
    this.appointmentRef = this.route.snapshot.params['reference'];

    this.getAppointment().subscribe({
      next: (appointment) => {
        this.appointment = appointment;
        this.fetchedAppointment = true;
        console.log(appointment);
      },
      error: (error) => {
        console.error('Error loading appointment:', error);
      },
    });

    this.getDiagnosis().subscribe({
      next: (diagnosis) => {
        this.diagnosis = diagnosis;
        this.fetchedDiagnosis = true;
        console.log(diagnosis);
      },
      error: (error) => {
        console.error('Error loading diagnosis:', error);
      },
    });
  }

  getAppointment(): Observable<Appointment> {
    return this.http.get<Appointment>(
      `${this.apiUrl}/specialists/appointments/ref/${this.appointmentRef}`
    );
  }

  getDiagnosis(): Observable<Diagnosis> {
    return this.http.get<Diagnosis>(
      `${this.apiUrl}/specialists/appointments/ref/${this.appointmentRef}/diagnosis`
    );
  }

  simplifyDiagnosis(): void {
    this.simplifying = true;
    this.http
      .get<Diagnosis>(
        `${this.apiUrl}/patients/appointment/${this.appointmentRef}/diagnosis/simplify`
      )
      .subscribe({
        next: (simplifiedDiagnosis) => {
          this.diagnosis = simplifiedDiagnosis;
          this.simplifying = false;
        },
        error: (error) => {
          console.error('Error simplifying diagnosis:', error);
        },
      });
  }
}
