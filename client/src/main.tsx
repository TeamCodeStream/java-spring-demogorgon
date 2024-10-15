import { ApiProvider } from '@reduxjs/toolkit/query/react';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ownersApi } from 'src/api/ownerReducers.tsx';
import App from './App.tsx';

import './index.scss';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter basename={"/react"}>
      <ApiProvider api={ownersApi}>
        <App />
      </ApiProvider>
    </BrowserRouter>
  </React.StrictMode>,
);
