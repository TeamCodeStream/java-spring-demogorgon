import { Route, Routes } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { OwnerDetails } from 'src/pages/OwnerDetails';
import { NavigationBar } from './navbar/NavigationBar';
import { Error } from './pages/Error';
import { FindOwner } from './pages/FindOwner';
import { Home } from './pages/Home';
import { OwnerForm } from './pages/OwnerForm';
import { OwnerList } from './pages/OwnerList';
import { Veterinarians } from './pages/Veterinarians';

function App() {
  return (
    <>
      <NavigationBar />
      <div className="container xd-container">
        <ToastContainer
          position="top-right"
          autoClose={2500}
          hideProgressBar
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          className="hide-in-print"
          theme="colored"
          icon={false}
        />
        <Routes>
          <Route index={true} path="/" element={<Home />} />
          <Route path="/oups" element={<Error />} />
          <Route path="/home" element={<Home />} />
          <Route path="/vets" element={<Veterinarians />} />
          <Route path="owners">
            <>
              <Route index={true} element={<OwnerList />} />
              <Route index={false} path="find" element={<FindOwner />} />
              <Route index={false} path="new" element={<OwnerForm />} />
              <Route index={false} path="modify/:id" element={<OwnerForm />} />
              <Route index={false} path=":id" element={<OwnerDetails />} />
            </>
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
