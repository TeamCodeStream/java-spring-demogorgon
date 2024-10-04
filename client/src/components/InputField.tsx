import { ChangeEventHandler } from "react";

interface InputFieldProps {
  label: string;
  type: string;
  name: string;
  value?: string;
  onChange?: ChangeEventHandler<HTMLInputElement | HTMLSelectElement>;
  required?: boolean;
  placeHolder: string;
  errors: string[];
}

export const InputField = ({
  label,
  type,
  name,
  placeHolder = '',
  errors = [],
  ...props
}: InputFieldProps) => {
  const showError =
    errors.length > 0 && errors[0] !== undefined && errors[0] !== null && errors[0] !== '';
  const inputType = type;
  return (
    <div className={`"form-group"${showError ? '-has-error' : ''}`}>
      <label className="col-sm-2 control-label" style={{ cursor: 'pointer', marginBottom: '-5px' }}>
        {label}
      </label>
      <div className="col-sm-10">
        <input className="form-control mb-1" type={inputType} name={name} placeholder={placeHolder} {...props} />
        {!showError && <span className="fa fa-ok form-control-feedback" aria-hidden="true"></span>}
      </div>
      {showError && errors.length > 1 && (
        <>
          {Array.isArray(errors) &&
            errors.map((object, idx) => {
              return (
                <div className="text-danger form-validation-error" key={idx}>
                  {object}
                </div>
              );
            })}
        </>
      )}
      {showError && errors.length === 1 && <div className="text-danger small">{errors}</div>}
    </div>
  );
};
