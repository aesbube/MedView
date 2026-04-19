import {Component, inject, OnInit} from '@angular/core';
import {SpecialistService} from '../../specialist.service';
import {ToastService} from '../../../../core/services/toast.service';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatIconButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {TranslatePipe} from '../../../../core/pipes/translate.pipe';
import {TranslationService} from '../../../../core/services/translation.service';

@Component({
  selector: 'app-specialist-details',
  templateUrl: './specialist-details.component.html',
  styleUrl: './specialist-details.component.css',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatIconModule,
    MatIconButton,
    RouterLink,
    MatProgressSpinner,
    TranslatePipe,
  ]
})
export class SpecialistDetailsComponent implements OnInit {
  service = inject(SpecialistService);
  fb = inject(FormBuilder);
  private toast = inject(ToastService);
  private ts = inject(TranslationService);
  form!: FormGroup;
  todayStr: string = new Date().toISOString().split('T')[0];

  ngOnInit(): void {
    this.service.getSpecialistDetails().subscribe((specialist) => {
      this.form = this.fb.group({
        username: [{value: specialist.username, disabled: true}],
        email: [{value: specialist.email, disabled: true}],
        phone: [specialist.phone],
        name: [specialist.name],
        surname: [specialist.surname],
        birthDate: [specialist.birthDate ?? ''],
        address: [specialist.address ?? ''],
        specialty: [specialist.specialty],
        licenseNumber: [specialist.licenseNumber],
        yearsOfExperience: [specialist.yearsOfExperience],
      });
    });
  }

  submit() {
    if (this.form.valid) {
      const updatedSpecialist = this.form.getRawValue();
      this.service.updateSpecialistDetails(updatedSpecialist).subscribe({
        next: () => this.toast.success(this.ts.translate('specialist.details.toast.success')),
        error: () => this.toast.error(this.ts.translate('specialist.details.toast.error'))
      });
    }
  }
}
