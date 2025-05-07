  import {Component} from '@angular/core';
  import {MatFormFieldModule} from '@angular/material/form-field';
  import {MatInputModule} from '@angular/material/input';
  import {MatTimepickerModule} from '@angular/material/timepicker';
  import {MatDatepickerModule} from '@angular/material/datepicker';
  import {FormsModule} from '@angular/forms';
  import {MatOption, provideNativeDateAdapter} from '@angular/material/core';
  import {MatCard} from '@angular/material/card';
  import {DatePipe, NgForOf} from '@angular/common';
  import { MatSelectModule } from '@angular/material/select';

  @Component({
    standalone: true,
    selector: 'app-appointment',
    templateUrl: './appointment.component.html',
    styleUrl: './appointment.component.css',
    providers: [provideNativeDateAdapter()],
    imports: [
      MatFormFieldModule,
      MatInputModule,
      MatTimepickerModule,
      MatDatepickerModule,
      FormsModule,
      MatCard,
      DatePipe,
      MatSelectModule,
      MatOption,
      NgForOf,
    ],
  })
  export class AppointmentComponent {
    value: Date = new Date();
    time: string = this.value.toTimeString().slice(0, 5);
    minDate: Date = new Date();
    maxDate: Date = new Date(new Date().setFullYear(new Date().getFullYear() + 1));

    timeOptions: string[] = [];

    constructor() {
      this.generateTimeOptions();
    }

    generateTimeOptions() {
      for (let hour = 8; hour < 17; hour++) {
        for (let minute of [0, 30]) {
          const h = hour.toString().padStart(2, '0');
          const m = minute.toString().padStart(2, '0');
          this.timeOptions.push(`${h}:${m}`);
        }
      }
    }
    onDateChange(date: Date) {
      const [hours, minutes] = this.time.split(':').map(Number);
      const updatedDate = new Date(date);
      updatedDate.setHours(hours, minutes, 0, 0);
      this.value = updatedDate;
    }
    onTimeChange(timeStr: string) {
      const [hours, minutes] = timeStr.split(':').map(Number);
      const newDate = new Date(this.value);
      newDate.setHours(hours, minutes, 0, 0);
      this.value = newDate;
    }

  }
