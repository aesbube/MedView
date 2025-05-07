import {Doctor} from './doctor.model';

export interface Patient {
  username: String,
  email: String,
  doctor: Doctor,
}
