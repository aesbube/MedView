import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators, } from '@angular/forms';
import { ValidationService } from '../../../core/services/validation.service';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ToastService } from '../../../core/services/toast.service';
import { TranslatePipe } from '../../../core/pipes/translate.pipe';
import { TranslationService } from '../../../core/services/translation.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule, TranslatePipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  myForm: FormGroup;
  successMessage: string | null = null;
  private toast = inject(ToastService);
  private ts = inject(TranslationService);

  constructor(
    public validationService: ValidationService,
    private authService: AuthService,
    private router: Router
  ) {
    this.myForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  onSubmit() {
    console.log('Submit');
    if (this.myForm.valid) {
      console.log('Logging in...');
      const LoginData = this.myForm.value;

      this.authService.login(LoginData).subscribe({
        next: (response) => {
          this.successMessage = null
          if (
            this.authService.getRole() == "ROLE_ADMIN" ||
            this.authService.getRole() == "ROLE_DOCTOR" ||
            this.authService.getRole() == "ROLE_PHARMACIST" ||
            this.authService.getRole() == "ROLE_SPECIALIST"
          )
            this.router.navigate(['/dashboard']);
          else
            this.router.navigate(['']);
          console.log('Login successful!', response);
          this.toast.success(this.ts.translate('login.toast.success'));
        },
        error: (error) => {
          this.successMessage = 'Wrong credentials';
          console.error('Login failed:', error);
          this.toast.error(this.ts.translate('login.toast.error'));
        },
      });
    } else {
      console.log('Form is invalid');
    }
  }
}
