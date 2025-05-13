import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ValidationService } from '../../../core/services/validation.service';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-login2',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './login2.component.html',
  styleUrl: './login2.component.css',
})
export class Login2Component {
  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

  myForm: FormGroup;
  successMessage: string | null = null;

  constructor(
    public validationService: ValidationService,
    private authService: AuthService,
    private router: Router
  ) {
    this.myForm = new FormGroup({
      username: this.usernameFormControl,
      password: this.passwordFormControl,
    });
  }

  onSubmit() {
    console.log('Submit');
    if (this.myForm.valid) {
      console.log('Logging in...');
      const LoginData = this.myForm.value;

      this.authService.login(LoginData).subscribe({
        next: (response) => {
          this.successMessage = null;
          if (
            this.authService.getRole() == 'ROLE_ADMIN' ||
            this.authService.getRole() == 'ROLE_DOCTOR' ||
            this.authService.getRole() == 'ROLE_PHARMACIST' ||
            this.authService.getRole() == 'ROLE_SPECIALIST'
          )
            this.router.navigate(['/dashboard']);
          else this.router.navigate(['']);
          console.log('Login successful!', response);
        },
        error: (error) => {
          this.successMessage = 'Wrong credentials';
          console.error('Login failed:', error);
        },
      });
    } else {
      console.log('Form is invalid');
    }
  }
}
