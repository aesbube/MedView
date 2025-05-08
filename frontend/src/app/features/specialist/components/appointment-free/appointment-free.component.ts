import { Component, OnInit } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { FormsModule } from '@angular/forms';
import { MatCard } from '@angular/material/card';
import { DatePipe } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { Appointment } from '../../../../models/appointment.model';
import { SpecialistService } from '../../specialist.service';

export interface FreeAppointment {
  date: Date;
  time: string;
  location: string;
}

@Component({
  selector: 'app-appointment-free',
  templateUrl: './appointment-free.component.html',
  styleUrls: ['./appointment-free.component.css'],
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    FormsModule,
    MatCard,
    DatePipe,
    MatSelectModule,
    MatIconModule,
    MatButtonModule,
    MatNativeDateModule
  ]
})
export class AppointmentFreeComponent implements OnInit {
  value: Date | null = null;
  time: string = '';
  location: string = '';
  minDate: Date = new Date(new Date().setHours(0, 0, 0, 0));
  maxDate: Date = new Date(new Date().setFullYear(this.minDate.getFullYear() + 1));
  timeOptions: string[] = [];
  disabledTimeOptions: Set<string> = new Set();
  occupiedAppointments: Appointment[] = [];
  selectedAppointments: { date: Date; location: string }[] = [];

  constructor(private specialistService: SpecialistService) {
    this.generateTimeOptions();
  }

  ngOnInit() {
    this.loadOccupiedAppointments();
  }

  extractOccupiedSlots() {
    for (const appointment of this.occupiedAppointments) {
      this.disabledTimeOptions.add(`${appointment.date} ${appointment.time}`);
    }
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
    if (!date) return;
    this.value = date;
    this.time = '';
    this.updateDisabledTimes();
  }

  onTimeChange(timeStr: string) {
    this.time = timeStr;
    if (!this.value) return;
  }

  addAppointment() {
    const [hours, minutes] = this.time.split(':').map(Number);
    const updatedDate = new Date(this.value!);
    updatedDate.setHours(hours, minutes, 0, 0);

    const alreadyExists = this.selectedAppointments.some(d =>
      this.isSameDateAndTime(d['date'], updatedDate)
    );

    if (!alreadyExists) {
      this.selectedAppointments.push({ date: updatedDate, location: this.location });
      this.updateDisabledTimes();
      this.selectNextAvailableTime();
    }
  }

  removeAppointment(index: number) {
    this.selectedAppointments.splice(index, 1);
    this.updateDisabledTimes();
  }

  submitAppointments() {
    const appointments: FreeAppointment[] = this.selectedAppointments.map(date => ({
      date: new Date(Date.UTC(date['date'].getFullYear(), date['date'].getMonth(), date['date'].getDate())),
      time: `${date['date'].getHours().toString().padStart(2, '0')}:${date['date'].getMinutes().toString().padStart(2, '0')}`,
      location: date['location']
    }));

    this.specialistService.setFreeAppointments(appointments).subscribe({
      next: () => {
        console.log('Appointments sent successfully');
        this.loadOccupiedAppointments();
      },
      error: (err) => {
        console.error('Failed to send appointments:', err);
      }
    });
  }

  isDateSelected(date: Date): boolean {
    return this.selectedAppointments.some(appointment =>
      this.isSameDay(appointment['date'], date)
    );
  }

  isTimeSlotBooked(date: Date, timeStr: string): boolean {
    const [hours, minutes] = timeStr.split(':').map(Number);
    return this.selectedAppointments.some(appointment =>
      this.isSameDay(appointment['date'], date) &&
      appointment['date'].getHours() === hours &&
      appointment['date'].getMinutes() === minutes
    );
  }

  getAvailableTimeOptions(date: Date): string[] {
    return this.timeOptions.filter(timeStr => !this.isTimeSlotBooked(date, timeStr));
  }

  private isSameDay(date1: Date, date2: Date): boolean {
    return date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate();
  }

  private isSameDateAndTime(date1: Date, date2: Date): boolean {
    return this.isSameDay(date1, date2) &&
      date1.getHours() === date2.getHours() &&
      date1.getMinutes() === date2.getMinutes();
  }

  private updateDisabledTimes() {
    this.disabledTimeOptions.clear();

    if (!this.value) return;

    const selectedDate = this.value;

    for (const appointment of this.selectedAppointments) {
      if (this.isSameDay(appointment['date'], selectedDate)) {
        const timeStr = `${appointment['date'].getHours().toString().padStart(2, '0')}:${appointment['date'].getMinutes().toString().padStart(2, '0')}`;
        this.disabledTimeOptions.add(timeStr);
      }
    }
  }

  private selectNextAvailableTime() {
    if (!this.value) return;

    const availableTime = this.timeOptions.find(timeOption => !this.disabledTimeOptions.has(timeOption));

    if (availableTime) {
      this.time = availableTime;
    }
  }

  dateClass = (date: Date) => {
    return this.isDateSelected(date) ? 'selected-date' : '';
  }

  onCalendarDateChange(date: Date) {
    this.value = date;
    this.updateDisabledTimes();
    this.selectNextAvailableTime();
  }

  loadOccupiedAppointments() {
    this.specialistService.getOccupiedAppointments().subscribe(appointments => {
      this.occupiedAppointments = appointments;

      this.selectedAppointments = appointments.map(app => {
        const [year, month, day] = app.date.split('-').map(Number);
        const [hour, minute] = app.time.split(':').map(Number);
        return {
          date: new Date(year, month - 1, day, hour, minute),
          location: app.location
        };
      });

      this.extractOccupiedSlots();
      this.updateDisabledTimes();
      this.selectNextAvailableTime();
    });
  }
}
