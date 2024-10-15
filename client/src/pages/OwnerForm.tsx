import { ChangeEvent, FormEventHandler, useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import {
  useChangeOwnerMutation,
  useCreateOwnerMutation,
  useGetOwnerQuery,
} from 'src/api/ownerReducers';
import { InputField } from 'src/components/InputField';
import { useTelephoneValidation } from 'src/hooks/useTelephoneValidation';

const defaultUserInfo = {
  firstName: '',
  lastName: '',
  city: '',
  address: '',
  telephone: '',
};

export const OwnerForm = () => {
  const params = useParams();
  const id = params['id'] ?? '';
  const newOwner = Boolean(!id);

  const { currentData: owner } = useGetOwnerQuery(id);

  const [userInfo, setUserInfo] = useState(owner ?? defaultUserInfo);

  const { telephoneErrors, validatePhoneNumberLength } = useTelephoneValidation();

  const [createOwner] = useCreateOwnerMutation();
  const [changeOwner] = useChangeOwnerMutation();

  const handleTelephoneChange = ({ target: { name, value } }: ChangeEvent<HTMLInputElement>) => {
    validatePhoneNumberLength(value);
    setUserInfo((prevData) => {
      return { ...prevData, [name]: value };
    });
  };

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setUserInfo((prevData) => {
      return { ...prevData, [name]: value };
    });
  };

  const handleSubmit: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    if (id) {
      changeOwner({ ownerId: id, payload: userInfo })
        .unwrap()
        .then(() => toast.success('Owner was updated successfully'))
        .catch(({ data }) =>
          data.errors.forEach((item: string) => {
            toast.error(item);
          }),
        );
    } else {
      createOwner(userInfo)
        .unwrap()
        .then(() => toast.success('Owner was updated successfully'))
        .catch(({ data }) =>
          data.errors.forEach((item: string) => {
            toast.error(item);
          }),
        );
    }
  };

  useEffect(() => {
    if (!owner) {
      return;
    }
    setUserInfo({
      firstName: owner.firstName,
      lastName: owner.lastName,
      city: owner.city,
      address: owner.address,
      telephone: owner.telephone,
    });
  }, [owner]);

  return (
    <>
      <h2>Owner</h2>
      <form className="form-horizontal" id="add-owner-form" method="post" onSubmit={handleSubmit}>
        <div className="form-group has-feedback">
          <InputField
            type="text"
            name="firstName"
            label="First Name"
            placeHolder="First name"
            value={userInfo.firstName}
            onChange={handleChange}
            required
            errors={[]}
          />

          <InputField
            type="text"
            name="lastName"
            label="Last Name"
            placeHolder="Last name"
            value={userInfo.lastName}
            onChange={handleChange}
            required
            errors={[]}
          />
          <InputField
            type="text"
            name="address"
            label="Address"
            placeHolder="Address"
            value={userInfo.address}
            onChange={handleChange}
            required
            errors={[]}
          />
          <InputField
            type="text"
            name="city"
            label="City"
            placeHolder="City"
            value={userInfo.city}
            onChange={handleChange}
            required
            errors={[]}
          />
          <InputField
            type="tel"
            name="telephone"
            label="Telephone"
            placeHolder="Telephone"
            value={userInfo.telephone}
            onChange={handleTelephoneChange}
            required
            errors={telephoneErrors}
          />
        </div>
        <div className="form-group">
          <div className="col-sm-offset-2 col-sm-10">
            <Button className="btn btn-primary" type="submit">
              {newOwner ? 'Add Owner' : 'Update Owner'}
            </Button>
          </div>
        </div>
      </form>
    </>
  );
};
