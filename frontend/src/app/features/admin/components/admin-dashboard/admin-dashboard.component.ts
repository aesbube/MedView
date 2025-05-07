import { Component } from '@angular/core';
import {RegisterAdminComponent} from '../../../auth/register-admin/register-admin.component';

@Component({
  selector: 'app-admin-dashboard',
  imports: [RegisterAdminComponent],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

}
