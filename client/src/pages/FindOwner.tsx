import { FormEventHandler } from 'react';
import { Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';

export const FindOwner = () => {
  const navigate = useNavigate();

  const handleFindOwner: FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    const formData = Object.fromEntries(new FormData(form).entries());
    goToOwnersListPage(formData['lastName'] as string);
  };

  const goToOwnersListPage = (lastName: string) => {
    navigate(lastName ? `/owners/?lastName=${lastName}` : '/owners');
  };

  return (
    <>
      <h2>Find Owners</h2>

      <form className="form-horizontal" id="search-owner-form" onSubmit={handleFindOwner}>
        <div className="form-group">
          <div className="control-group" id="lastNameGroup">
            <label className="col-sm-2 control-label">Last name </label>
            <div className="col-sm-10">
              <input className="form-control" maxLength={80} name="lastName" />
            </div>
          </div>
        </div>
        <div className="form-group">
          <div className="col-sm-offset-2 col-sm-10">
            <Button className="btn btn-primary" type="submit">
              Find Owner
            </Button>
          </div>
        </div>

        <Link className="btn btn-primary" to="/owners/new">
          Add Owner
        </Link>
      </form>
    </>
  );
};
