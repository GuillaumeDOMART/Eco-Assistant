import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
//import React, {useEffect, useState} from "react";
import {Container, Row} from "react-bootstrap";

export default function QuestionProposee() {
    return (
        <div id="app" style={{minWidth: '500px'}} className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5 vh-100">
                <QuestionProposeeFromApi/>
            </div>
        </div>
    );
}

function QuestionProposeeFromApi() {

    return (
        <>
            <Row className="justify-content-start p-3">
                <h1 className="text-start">Questions proposées</h1>
            </Row>
            <Container className="d-flex align-items-center" style={{height: "50%"}}>
                <Row className="">
                    <h4>Si vous avez idées de questions à ajouter au questionnaire Eco-Assistant, n'hésitez pas à
                        envoyer un mail à :
                        <br/><br/>
                        <b>eco.assistant.esipe.lp@gmail.com</b>
                        <br/><br/>
                        Ajoutez en sujet du mail « [Proposer une question] » pour que l'on puisse prendre en compte ta
                        proposition.
                        <br/><br/>
                        N'hésitez pas à proposer des sources et des données pour appuyer votre proposition et à
                        mentionner la/les phases dans lesquelles elle pourrait s'insérer dans le questionnaire.
                    </h4>
                </Row>
            </Container>
        </>
    );
}

/*
function QuestionProposeeError(apiError) {
    return (
        <Alert variant="danger">
            <Alert.Heading>Error</Alert.Heading>
            {apiError.message}
        </Alert>
    );
}

function QuestionProposeeLoading() {
    return (
        <Spinner variant="primary"/>
    );
}

function QuestionProposeeValide(data) {
    return <p>CA MARCHE !</p>
}
*/
