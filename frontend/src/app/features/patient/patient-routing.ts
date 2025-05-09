import { Routes } from '@angular/router';
import { PatientAppointmentsComponent } from './components/patient-appointments/patient-appointments.component';
import { PatientPrescriptionsComponent } from './components/patient-prescriptions/patient-prescriptions.component';
import { PatientHistoryComponent } from './components/patient-history/patient-history.component';
import { PatientInfoComponent } from './components/patient-info/patient-info.component';

export const patientRoutes: Routes = [
  { path: 'info', component: PatientInfoComponent },
  { path: 'appointments', component: PatientAppointmentsComponent },
  { path: 'prescriptions', component: PatientPrescriptionsComponent },
  { path: 'history', component: PatientHistoryComponent },
];
