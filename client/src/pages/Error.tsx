import pets from '/src/assets/images/pets.png';

export const Error = () => {
  return (
    <>
      <div className="row">
        <div className="col-md-12">
          <img className="img-responsive" src={pets} />
        </div>
      </div>
      <h2>Something happened...</h2>
    </>
  );
};
