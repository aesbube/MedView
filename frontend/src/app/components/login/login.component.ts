import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ValidationService } from '../../core/services/validation.service';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  myForm: FormGroup;

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

  ngOnInit() {}

  onSubmit() {
    console.log('Submit');
    if (this.myForm.valid) {
      console.log('Logging in...');
      const LoginData = this.myForm.value;

      this.authService.login(LoginData).subscribe({
        next: (response) => {
          console.log('Login successful!', response);
          this.router.navigate(['']);
        },
        error: (error) => {
          console.error('Login failed:', error);
        },
      });
    } else {
      console.log('Form is invalid');
    }
  }
}
