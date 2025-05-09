import { Component, Input } from '@angular/core';
import { Prescription } from '../../../models/prescription.model';
import { MatCard, MatCardContent, MatCardHeader, MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-prescription-details',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardModule
  ],
  templateUrl: './prescription-details.component.html',
  styleUrl: './prescription-details.component.css'
})
export class PrescriptionDetailsComponent {
  @Input() prescription!: Prescription; 
}
