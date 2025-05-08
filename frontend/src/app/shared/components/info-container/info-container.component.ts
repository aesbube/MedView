import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from '@angular/router';
import {DoctorDetailsComponent} from '../../../features/doctor/components/doctor-details/doctor-details.component';
import {
  SpecialistDetailsComponent
} from '../../../features/specialist/components/specialist-details/specialist-details.component';
import {Roles} from '../../../models/roles.model';

@Component({
  selector: 'app-info-container',
  imports: [
    DoctorDetailsComponent,
    SpecialistDetailsComponent
  ],
  templateUrl: './info-container.component.html',
  styleUrl: './info-container.component.css'
})
export class InfoContainerComponent implements OnInit {
  role: string | null = null;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.role = this.authService.getRole();
    if (!["ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_SPECIALIST", "ROLE_PHARMACIST", "ROLE_ADMIN"].includes(this.role ?? '')) {
      this.router.navigate(['']);
    }
  }

  protected readonly Roles = Roles;
}
