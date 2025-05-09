import { PrescriptionStatus } from "./prescription-status";

export interface Prescription {
  id: Number,
  patientId: Number,
  patientNameAndSurname: String,
  doctorId: Number,
  doctorNameAndSurname: String,
  medicine: String,
  frequency: String,
  status: PrescriptionStatus,
  expirationDate: Date,
}
