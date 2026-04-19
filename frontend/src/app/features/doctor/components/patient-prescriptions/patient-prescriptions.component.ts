import {Component, inject, Input, OnInit} from '@angular/core';
import {DoctorService} from '../../doctor.service';
import {Prescription} from '../../../../models/prescription.model';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatIconModule} from '@angular/material/icon';
import {RouterLink} from '@angular/router';
import {PrescriptionDetailsComponent} from '../../../../shared/components/prescription-details/prescription-details.component';
import {TranslatePipe} from '../../../../core/pipes/translate.pipe';

@Component({
  selector: 'app-patient-prescriptions',
  imports: [
    MatProgressSpinner,
    MatIconModule,
    RouterLink,
    PrescriptionDetailsComponent,
    TranslatePipe,
  ],
  templateUrl: './patient-prescriptions.component.html',
  styleUrl: './patient-prescriptions.component.css'
})
export class PatientPrescriptionsComponent implements OnInit {
  service = inject(DoctorService);
  prescriptions: Prescription[] = [];
  prescriptionsLoaded = false;
  @Input() patientId!: string;

  ngOnInit(): void {
    this.service.getPatientPrescriptions(this.patientId).subscribe({
      next: (prescriptions) => {
        this.prescriptions = prescriptions;
        this.prescriptionsLoaded = true;
      },
      error: () => this.prescriptionsLoaded = true
    });
  }
}
