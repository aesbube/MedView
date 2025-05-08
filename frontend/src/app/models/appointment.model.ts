export interface Appointment {
  scheduleId: number;
  patientName: string | null;
  assigneeName: string | null;
  date: string;
  time: string;
  location: string;
  refNumber: string;
  status: string;
}
