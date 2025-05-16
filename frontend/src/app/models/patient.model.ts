import { BloodType } from './blood-type.model';
import {Doctor} from './doctor.model';

export interface Patient {
  id: number,
  username: string,
  email: string,
  doctor: Doctor,
  name: string,
  surname: string,
  phone: string,
  address: string,
  birthDate: Date,
  birthPlace: string,
  allergies: string,
  bloodType: BloodType,
}
