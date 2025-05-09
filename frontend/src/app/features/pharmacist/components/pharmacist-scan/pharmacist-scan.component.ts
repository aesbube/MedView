import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-pharmacist-scan',
  imports: [
    ZXingScannerModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './pharmacist-scan.component.html',
  styleUrl: './pharmacist-scan.component.css'
})
export class PharmacistScanComponent {
  qrResultString: string | null = null;
  prescriptionId = ""
  patientId = ""

  clearResult(): void {
    this.qrResultString = null;
  }

  onCodeResult(resultString: string) {
    this.qrResultString = resultString;
    this.prescriptionId = this.qrResultString.substring(0,12)
    this.patientId = this.qrResultString.substring(12,this.qrResultString.length)
  }

}
