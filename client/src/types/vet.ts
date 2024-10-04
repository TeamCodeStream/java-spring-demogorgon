import { Person } from 'src/types/owner';

export interface Veterinarian extends Person {
  specialties: Specialty[];
}

export interface Specialty {
  name: string;
}
