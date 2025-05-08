import { Component, inject, OnInit } from '@angular/core';
import { SpecialistService } from '../../specialist.service';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButton, MatIconButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinner} from '@angular/material/progress-spinner';

@Component({
  selector: 'app-specialist-details',
  templateUrl: './specialist-details.component.html',
  styleUrl: './specialist-details.component.css',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatIconModule, MatIconButton, RouterLink, MatProgressSpinner, MatButton]
})
export class SpecialistDetailsComponent implements OnInit {
  service = inject(SpecialistService);
  fb = inject(FormBuilder);
  form!: FormGroup;

  ngOnInit(): void {
    this.service.getSpecialistDetails().subscribe((specialist) => {
      this.form = this.fb.group({
        username: [{ value: specialist.username, disabled: true }],
        email: [specialist.email],
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
        next: (ok) => {
          console.log('Update successful', ok);
        },
        error: (err) => {
          console.error('Update failed', err);
        }
      });
    }
  }
}
