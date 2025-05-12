import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Prescription } from '../../../models/prescription.model';
import { MatCard, MatCardContent, MatCardHeader, MatCardModule } from '@angular/material/card';
import { AuthService } from '../../../core/services/auth.service';
import { PrescriptionStatus } from '../../../models/prescription-status';
import { QrCodeComponent } from '../qr-code/qr-code.component';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-prescription-details',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardModule,
    QrCodeComponent,
    MatButtonModule
  ],
  templateUrl: './prescription-details.component.html',
  styleUrl: './prescription-details.component.css'
})
export class PrescriptionDetailsComponent {
  @Input() prescription!: Prescription;
  @Input() isDoctor: boolean = false;
  @Input() prescription!: Prescription;
  @Output() validate = new EventEmitter<boolean>()
  PrescriptionStatus = PrescriptionStatus

  authService = inject(AuthService)
  userRole: String = "ROLE_GUEST"
  uniqueQrText = ""
  redeemed = false
  ngOnInit(){
    this.userRole = this.authService.getRole()
    this.uniqueQrText = this.prescription.id+"-"+this.authService.getUserId()
  }

  emitValidation(){
    this.validate.emit(true)
  }
}
