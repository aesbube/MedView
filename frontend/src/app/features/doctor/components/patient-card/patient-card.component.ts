import { Component, Input } from '@angular/core';
import { Patient } from '../../../../models/patient.model';
import { MatCard, MatCardHeader, MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import {MatIconButton} from '@angular/material/button';

@Component({
  selector: 'app-patient-card',
  imports: [
    MatCard,
    MatCardHeader,
    MatCardModule,
    RouterLink,
    MatIconButton,
  ],
  templateUrl: './patient-card.component.html',
  styleUrl: './patient-card.component.css'
})
export class PatientCardComponent {
  @Input() patient!: Patient;
}
