import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import './scss/custom.scss'

import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import ErrorPage from "./ErrorPage";
import ResultPage from "./ResultPage/ResultPage";
import ConnexionPage from "./Connexion/ConnexionPage";
import ForgotPassword from "./ForgotPassword/ForgotPassword";
import AccueilSite from "./AccueilSite/AccueilSite";
import AccueilProfil from "./AccueilProfil/AccueilProfil";
import Profil from "./InfoProfil/Profil";
import StepperComponent from "./Stepper/Stepper";
import CreateProject from "./Quiz/CreateProject";
import Deconnection from "./Deconnection/LogOut";

const router = createBrowserRouter([
    {
        path: "/",
        element: <AccueilSite/>,
        errorElement: <ErrorPage/>,
    },
    {
        path: "/profil",
        element: <AccueilProfil/>,
    },
    {
      path: "/connexion",
      element: <ConnexionPage/>,
    },
    {
        path:"/questionnaire",
        element: <StepperComponent/>,
    },
    {
      path:"/newproject",
      element: <CreateProject/>
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
    },
    {
        path: "/infoProfil",
        element: <Profil/>
    },{
        path:"/logout",
        element: <Deconnection/>
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
