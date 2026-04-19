import {Component, inject, OnInit} from '@angular/core';
import {DoctorService} from '../../doctor.service';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {WritePrescriptionModel} from '../../../../models/write-prescription.model';
import {MatIconModule} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {ToastService} from '../../../../core/services/toast.service';
import {TranslatePipe} from '../../../../core/pipes/translate.pipe';
import {TranslationService} from '../../../../core/services/translation.service';

@Component({
  selector: 'app-write-prescription',
  imports: [
    ReactiveFormsModule,
    MatIconModule,
    MatIconButton,
    RouterLink,
    TranslatePipe,
  ],
  templateUrl: './write-prescription.component.html',
  styleUrl: './write-prescription.component.css'
})
export class WritePrescriptionComponent implements OnInit {
  service = inject(DoctorService);
  route = inject(ActivatedRoute);
  fb = inject(FormBuilder);
  private toast = inject(ToastService);
  private ts = inject(TranslationService);

  form!: FormGroup;
  patientId: number | null = null;
  isSubmitting = false;

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) this.patientId = +id;
    });

    this.form = this.fb.group({
      medicine: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      frequency: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]]
    });
  }

  submit() {
    if (this.form.valid && this.patientId !== null) {
      this.isSubmitting = true;
      const prescription: WritePrescriptionModel = this.form.value;
      this.service.writePrescription(this.patientId, prescription).subscribe({
        next: () => {
          this.toast.success(this.ts.translate('doctor.prescription.toast.success'));
          this.isSubmitting = false;
          this.form.reset();
        },
        error: () => {
          this.toast.error(this.ts.translate('doctor.prescription.toast.error'));
          this.isSubmitting = false;
        }
      });
    }
  }

  get medicineControl() { return this.form.get('medicine'); }
  get frequencyControl() { return this.form.get('frequency'); }
}
