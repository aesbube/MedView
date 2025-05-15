import { Component, model } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Specialist } from '../../../models/specialist.model';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, switchMap } from 'rxjs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { Schedule } from '../../../models/schedule.model';
import { Appointment } from '../../../models/appointment.model';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';

@Component({
  selector: 'app-specialist',
  imports: [
    MatProgressSpinnerModule,
    MatDividerModule,
    MatCardModule,
    MatDatepickerModule,
  ],
  templateUrl: './specialist.component.html',
  styleUrl: './specialist.component.css',
})
export class SpecialistComponent {
  selected = model<Date>(new Date());

  private apiUrl = environment.apiUrl;
  specialistUserName!: string;
  specialist: Specialist | null = null;
  appointments: Appointment[] = [];
  fetchedSpecialist = false;
  fetchedAppointments = false;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit() {
    this.specialistUserName = this.route.snapshot.params['username'];
    console.log('Helelo');

    this.getSpecialist()
      .pipe(
        switchMap((specialist) => {
          this.specialist = specialist;
          this.fetchedSpecialist = true;
          return this.getAvailableAppointments();
        })
      )
      .subscribe({
        next: (appointments) => {
          this.appointments = appointments;
          this.fetchedAppointments = true;
          console.log(appointments);
        },
        error: (error) => {
          console.error('Error loading specialist or appointments:', error);
        },
      });
  }

  getSpecialist(): Observable<Specialist> {
    return this.http.get<Specialist>(
      `${this.apiUrl}/specialists/${this.specialistUserName}`
    );
  }

  getSchedule(): Observable<Schedule> {
    return this.http.get<Schedule>(
      `${this.apiUrl}/specialists/schedule?specialistId=${this.specialist?.id}`
    );
  }

  getAvailableAppointments() {
    return this.http.get<Appointment[]>(
      `${this.apiUrl}/doctors/appointments/all/${this.specialist?.username}`
    );
  }

  formatDateDMY(date: Date | null): string {
    if (!date) return '';

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    return `${day}-${month}-${year}`;
  }

  formatDateYMD(date: Date | null): string {
    if (!date) return '';

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${year}-${month}-${day}`;
  }
}
