import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import './scss/custom.scss'

import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Questionnaire from "./Quiz/Questionnaire";
import ErrorPage from "./ErrorPage";
import ResultPage from "./ResultPage/ResultPage";
import AccueilSite from "./AccueilSite";
import ConnexionPage from "./Connexion/ConnexionPage";
import ForgotPassword from "./ForgotPassword/ForgotPassword";

const router = createBrowserRouter([
    {
        path: "/",
        element: <AccueilSite/>,
        errorElement: <ErrorPage/>,
    },
    {
        path: "/profil",
        element: <App/>,
        errorElement: <ErrorPage/>,
    },
    // {
    //     path: "/connexion",
    //     element: <AccueilSite/>,
    // },
    {
        path:"/questionnaire",
        element: <Questionnaire />,
    }
    ,{
        path:"/result",
        element: <ResultPage/>
    },
    {
        path:"/login",
        element: <ConnexionPage/>
    },
    {
        path:"/forgotPassword",
        element: <ForgotPassword/>
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
