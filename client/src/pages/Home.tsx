import pets from '/src/assets/images/pets.png';

export const Home = () => {
  return (
    <>
      <h2>Welcome</h2>
      <div className="row">
        <div className="col-md-12">
          <img className="img-responsive" src={pets} />
        </div>
      </div>
    </>
  );
};
