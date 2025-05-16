import { CommonModule } from '@angular/common';
import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import QRCode from 'qrcode';

@Component({
  selector: 'app-qr-code',
  imports: [FormsModule, CommonModule],
  templateUrl: './qr-code.component.html',
  styleUrl: './qr-code.component.css',
})
export class QrCodeComponent {
  qrText: string = 'Hello';
  @ViewChild('qrCanvas', { static: false }) qrCanvas!: ElementRef;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(){
    this.qrText = this.route.snapshot.paramMap.get('qrText') || "Hello"
  }

  ngAfterViewInit(): void {
    this.generateQrCode();
  }

  onInputChange(event: Event): void {
    this.generateQrCode();
  }

  generateQrCode(): void {
    QRCode.toCanvas(
      this.qrCanvas.nativeElement,
      this.qrText,
      {
        errorCorrectionLevel: 'H',
        scale: 30
      },
      (error: any) => {
        if (error) {
          console.error('Error generating QR code', error);
        } else {
          console.log('QR code generated successfully!');
        }
      }
    );
  }
}
