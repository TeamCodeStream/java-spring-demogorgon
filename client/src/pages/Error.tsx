import pets from '/src/assets/images/pets.png';

export const Error = () => {


    const numbers = [0,1,2];
    const theNumber = numbers[10];

  return (
    <>
      <div className="row">
        <div className="col-md-12">
          <img className="img-responsive" src={pets} />
        </div>
      </div>
      <h2>Something happened...</h2>
        <h3>{theNumber}</h3>
    </>
  );
};
