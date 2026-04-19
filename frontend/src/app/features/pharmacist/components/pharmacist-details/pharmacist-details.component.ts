import {Component, inject, OnInit} from '@angular/core';
import {PharmacistService} from '../../pharmacist.service';
import {ToastService} from '../../../../core/services/toast.service';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatIconButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {TranslatePipe} from '../../../../core/pipes/translate.pipe';
import {TranslationService} from '../../../../core/services/translation.service';

@Component({
  selector: 'app-pharmacist-details',
  templateUrl: './pharmacist-details.component.html',
  styleUrl: './pharmacist-details.component.css',
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
export class PharmacistDetailsComponent implements OnInit {
  service = inject(PharmacistService);
  fb = inject(FormBuilder);
  private toast = inject(ToastService);
  private ts = inject(TranslationService);
  form!: FormGroup;
  todayStr: string = new Date().toISOString().split('T')[0];

  ngOnInit(): void {
    this.service.getPharmacistDetails().subscribe((pharmacist) => {
      this.form = this.fb.group({
        username: [{value: pharmacist.username, disabled: true}],
        email: [{value: pharmacist.email, disabled: true}],
        name: [pharmacist.name ?? ''],
        surname: [pharmacist.surname ?? ''],
        phone: [pharmacist.phone ?? ''],
        address: [pharmacist.address ?? ''],
        birthDate: [pharmacist.birthDate ?? ''],
        pharmacyName: [pharmacist.pharmacyName ?? ''],
        pharmacyAddress: [pharmacist.pharmacyAddress ?? ''],
        licenseNumber: [pharmacist.licenseNumber ?? ''],
      });
    });
  }

  submit() {
    if (this.form.valid) {
      const updated = this.form.getRawValue();
      this.service.updatePharmacistDetails(updated).subscribe({
        next: () => this.toast.success(this.ts.translate('pharmacist.details.toast.success')),
        error: () => this.toast.error(this.ts.translate('pharmacist.details.toast.error'))
      });
    }
  }
}
