import { Doctor } from "./doctor.model";
import { Patient } from "./patient.model";
import { Schedule } from "./schedule.model";

export interface Appointment {
  id: number;
  schedule: Schedule;
  patient: Patient | null;
  assignee: Doctor | null;
  date: string;
  time: string;
  location: string;
  refNumber: string;
  status: string;
}
