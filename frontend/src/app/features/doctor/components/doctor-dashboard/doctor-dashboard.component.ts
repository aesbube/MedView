import {Component, OnInit} from '@angular/core';
import {AppointmentComponent} from '../appointment/appointment.component';

@Component({
  standalone: true,
  selector: 'app-doctor-dashboard',
  imports: [AppointmentComponent],
  templateUrl: './doctor-dashboard.component.html',
  styleUrl: './doctor-dashboard.component.css'
})
export class DoctorDashboardComponent implements OnInit {

  ngOnInit(): void {

  }

}
