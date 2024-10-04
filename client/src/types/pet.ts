import { Visit } from "src/types/visit";

export interface PetType {
  name: string;
}

export interface Pet {
  name: string;
  birthDate: Date;
  type: PetType;
  visits: Visit[];
}
