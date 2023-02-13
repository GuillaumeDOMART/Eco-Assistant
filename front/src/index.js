import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';

import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Questionnaire from "./Quiz/Questionnaire";
import ErrorPage from "./ErrorPage";
import ResultPage from "./ResultPage/ResultPage";
import Connection from "./Connection/Connection";
import App from "./App";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />,
        errorElement: <ErrorPage/>,
    },
    {
        path: "/connexion",
        element: <Connection/>,
    },
    {
        path:"/questionnaire",
        element: <Questionnaire />,
    }
    ,{
        path:"/result",
        element: <ResultPage/>
    }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
