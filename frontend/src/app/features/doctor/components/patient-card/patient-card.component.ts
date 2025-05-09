import { Component, Input } from '@angular/core';
import { Patient } from '../../../../models/patient.model';
import { MatCard, MatCardHeader, MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-patient-card',
  imports: [
    MatCard,
    MatCardHeader,
    MatCardModule,
    RouterLink,
  ],
  templateUrl: './patient-card.component.html',
  styleUrl: './patient-card.component.css'
})
export class PatientCardComponent {
  @Input() patient!: Patient;
}
