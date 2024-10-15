import { Pet } from 'src/types/pet';

export interface Person {
  id: number;
  firstName: string;
  lastName: string;
}

export interface Owner extends Person {
  address: string;
  city: string;
  telephone: string;
  pets: Pet[];
}
