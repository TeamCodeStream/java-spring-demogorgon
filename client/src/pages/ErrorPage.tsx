import pets from '/src/assets/images/pets.png';

const mapping = new Map<number, string>([
    [1,"Dogs"],
    [2,"Cats"],
    [3,"Horses"],
    [4,"Iguanas"],
    [5,"Goblin Sharks"]
])

export const ErrorPage = () => {
    const renderListItem = (index: number) => {
        const item = mapping.get(index);

        if(!item){
            throw new Error(`No mapping found for index: ${index}`);
        }

        return (
            <li>{item}</li>
        )
    }

    return (
        <>
          <div className="row">
            <div className="col-md-12">
              <img className="img-responsive" src={pets} />
            </div>
          </div>
          <h3>We currently work with the following animal species:</h3>
          <ol>
              {[1,2,3,4,5,6].map(i => renderListItem(i))}
          </ol>
        </>
    );
};
