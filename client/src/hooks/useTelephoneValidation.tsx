import { useCallback, useState } from 'react';

const TELEPHONE_NUMBER_LENGTH = 12;

export function useTelephoneValidation() {
  const [telephoneErrors, setTelephoneErrors] = useState<string[]>([]);

  const validatePhoneNumberLength = useCallback((phoneNumber: string) => {
    const errors = [...telephoneErrors];
    const lengthErrorMessages = {
      tooLong: 'Phone number too long',
      tooShort: 'Phone number not long enough',
      notStartWithPlus: 'Phone number should start with "+"',
    };

    const removeError = (message: string) => {
      const index = errors.indexOf(message);
      if (index !== -1) {
        errors.splice(index, 1);
      }
    };

    removeError(lengthErrorMessages.tooLong);
    removeError(lengthErrorMessages.tooShort);
    removeError(lengthErrorMessages.notStartWithPlus);

    if (phoneNumber) {
      if (!phoneNumber.startsWith('+')) {
        errors.push(lengthErrorMessages.notStartWithPlus);
      }
      if (phoneNumber.length > TELEPHONE_NUMBER_LENGTH) {
        errors.push(lengthErrorMessages.tooLong);
      }
      if (phoneNumber.length < TELEPHONE_NUMBER_LENGTH) {
        errors.push(lengthErrorMessages.tooShort);
      }
    }

    setTelephoneErrors(errors);
  }, []);
  return { validatePhoneNumberLength, telephoneErrors };
}
