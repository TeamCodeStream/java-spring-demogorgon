import { useGetVeterinariansQuery } from 'src/api/veterinerianReducer';
import { usePaging } from '../hooks/usePaging';

export const Veterinarians = () => {
  const { data } = useGetVeterinariansQuery();
  const [currentItems, paginator] = usePaging({ itemsPerPage: 3, items: data ?? [] });

  return (
    <>
      <h2 className="mb-2">Veterinarians</h2>
      <table id="vets" className="table table-striped mb-4">
        <thead>
          <tr>
            <th>Name</th>
            <th>Specialties</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(currentItems) &&
            currentItems.map(
              (vet: { firstName: string; lastName: string; specialties: { name: string }[] }) => {
                return (
                  <tr key={`${vet.firstName + ' ' + vet.lastName}`}>
                    <td>{`${vet.firstName + ' ' + vet.lastName}`}</td>
                    <td>
                      {vet.specialties.map((specialty) => {
                        return <span>{specialty.name}</span>;
                      })}
                      {vet.specialties.length < 1 && <span>none</span>}
                    </td>
                  </tr>
                );
              },
            )}
        </tbody>
      </table>
      {paginator}
    </>
  );
};
