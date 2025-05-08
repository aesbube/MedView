import { Routes } from '@angular/router';
import { BasicInfoComponent } from './components/basic-info/basic-info.component';
import { PatientAppointmentsComponent } from './components/patient-appointments/patient-appointments.component';
import { PatientPrescriptionsComponent } from './components/patient-prescriptions/patient-prescriptions.component';
import { PatientHistoryComponent } from './components/patient-history/patient-history.component';

export const patientRoutes: Routes = [
  { path: 'info', component: BasicInfoComponent },
  { path: 'appointments', component: PatientAppointmentsComponent },
  { path: 'prescriptions', component: PatientPrescriptionsComponent },
  { path: 'history', component: PatientHistoryComponent },
];
