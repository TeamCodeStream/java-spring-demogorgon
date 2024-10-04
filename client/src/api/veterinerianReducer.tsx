import { ownersApi } from 'src/api/ownerReducers';
import { Veterinarian } from 'src/types/vet';

const veterinariansApi = ownersApi.injectEndpoints({
  endpoints: (builder) => {
    return {
      getVeterinarians: builder.query<Veterinarian[], void>({
        query: () => {
          return { url: '/vets', method: 'get' };
        },
      }),
    };
  },
  overrideExisting: false,
});

export const { useGetVeterinariansQuery } = veterinariansApi;
