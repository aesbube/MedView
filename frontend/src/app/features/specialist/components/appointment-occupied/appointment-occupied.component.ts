import {Component, inject, OnInit} from '@angular/core';
import {Appointment} from '../../../../models/appointment.model';
import {SpecialistService} from '../../specialist.service';
import {Observable, switchMap} from 'rxjs';
import {AsyncPipe} from '@angular/common';
import {
  AppointmentDetailsComponent
} from '../../../../shared/components/appointment-details/appointment-details.component';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatIconButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-appointment-occupied',
  imports: [
    AsyncPipe,
    AppointmentDetailsComponent,
    MatProgressSpinner,
    MatIconButton,
    RouterLink,
    MatIconModule
  ],
  templateUrl: './appointment-occupied.component.html',
  styleUrl: './appointment-occupied.component.css'
})
export class AppointmentOccupiedComponent implements OnInit {
  service = inject(SpecialistService);
  occupiedAppointments$?: Observable<Appointment[]>

  ngOnInit(): void {
    this.loadOccupiedAppointments()
  }

  loadOccupiedAppointments() {
    this.occupiedAppointments$ = this.service.getOccupiedAppointments()
  }
}
