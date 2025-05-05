import { Routes } from '@angular/router';
import { HomeComponent } from './shared/components/home/home.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { AuthRedirectGuard } from './core/guards/auth-redirect.guard';
import { RoleGuard } from './core/guards/role.guard';
import { DashboardContainerComponent } from './shared/components/dashboard-container/dashboard-container.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'dashboard',
    component: DashboardContainerComponent,
    canActivate: [RoleGuard],
    data: {"expectedRole":["ROLE_PATIENT","ROLE_DOCTOR","ROLE_SPECIALIST","ROLE_PHARMACIST","ROLE_ADMIN"]}
  },
  { path: 'login', component: LoginComponent, canActivate: [AuthRedirectGuard] },
  { path: 'register', component: RegisterComponent },
];
