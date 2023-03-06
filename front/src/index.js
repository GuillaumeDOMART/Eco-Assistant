import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import './Components/scss/custom.scss'

import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import ErrorPage from "./ErrorPage";
import ResultPage from "./Views/ResultPage/ResultPage";
import ConnexionPage from "./Views/Connexion/ConnexionPage";
import AccueilSite from "./Views/AccueilSite/AccueilSite";
import AccueilProfil from "./Views/AccueilProfil/AccueilProfil";
import Profil from "./Views/InfoProfil/Profil";
import StepperComponent from "./Views/Quiz/Quiz";
import CreateProject from "./Views/Quiz/CreateProject";
import Deconnection from "./Views/Deconnection/LogOut";
import Guest from "./Views/Guest/Guest";
import ForgotPassword from "./Views/ForgotPassword/ForgotPassword";
import ModifyPassword from "./Views/ModifyPassword/ModifyPassword";
import ModifyID from "./Views/ModifyID/ModifyID";
import ForgotPasswordMail from "./Views/ForgotPassword/ForgotPasswordMail";
import ProfilVerifyMail from "./Views/CreationProfile/VerifyMail";
import QuestionProposee from "./Views/QuestionProposée/QuestionProposee";
import Moderation from "./Views/Moderation/Moderation";
import ModifyIDVerify from "./Views/ModifyID/ModifyIDVerify";

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
        path: "/questionnaire",
        element: <StepperComponent/>,
    },
    {
        path: "/newproject",
        element: <CreateProject/>
    }
    , {
        path: "/result",
        element: <ResultPage/>
    },
    {
        path: "/login",
        element: <ConnexionPage/>
    },
    {
        path: "/forgotPassword",
        element: <ForgotPassword/>
    },
    {
        path:"/forgotPasswordMail",
        element: <ForgotPasswordMail/>
    },
    {
      path:"/modifyPassword",
      element: <ModifyPassword/>
    },
    {
        path: "/modifyID",
        element: <ModifyID/>
    },
    {
        path: "/infoProfil",
        element: <Profil/>
    }, {
        path: "/logout",
        element: <Deconnection/>
    }, {
        path: "/guest",
        element: <Guest/>
    },
    {
        path: "/verifyMail",
        element: <ProfilVerifyMail/>
    }, {
        path: "/questionsProposees",
        element: <QuestionProposee/>
    }, {
        path: "/moderation",
        element: <Moderation/>
    },
    {
        path: "/modifyIDVerify",
        element: <ModifyIDVerify/>
    }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
