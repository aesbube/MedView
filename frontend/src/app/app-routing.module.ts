import {Routes} from '@angular/router';
import {HomeComponent} from './shared/components/home/home.component';
import {Home2Component} from './shared/components/home2/home2.component';
import {LoginComponent} from './features/auth/login/login.component';
import {RegisterComponent} from './features/auth/register/register.component';
import {AuthRedirectGuard} from './core/guards/auth-redirect.guard';
import {RoleGuard} from './core/guards/role.guard';
import {DashboardContainerComponent} from './shared/components/dashboard-container/dashboard-container.component';
import {AppointmentComponent} from './shared/components/appointment/appointment.component';
import {SpecialistComponent} from './shared/components/specialist/specialist.component';

export const routes: Routes = [
  {path: '', component: Home2Component},
  {
    path: 'dashboard',
    component: DashboardContainerComponent,
    canActivate: [RoleGuard],
    data: {"expectedRole": ["ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_SPECIALIST", "ROLE_PHARMACIST", "ROLE_ADMIN"]}
  },
  {path: 'login', component: LoginComponent, canActivate: [AuthRedirectGuard]},
  {path: 'register', component: RegisterComponent},

  {path: 'appointment/:id', component: AppointmentComponent},
  {path: 'specialist/:id', component: SpecialistComponent}
];
