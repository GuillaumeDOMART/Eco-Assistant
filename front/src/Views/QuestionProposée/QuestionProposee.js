import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useEffect, useState} from "react";
import {Alert, Container, Spinner} from "react-bootstrap";
export default function QuestionProposee(){
    return(
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5">
                <QuestionProposeeFromApi/>
            </div>
        </div>
    );
}

function QuestionProposeeFromApi(){
    const [isLoaded, setIsLoaded] = useState(false);
    const [apiError, setApiError] = useState(null);
    const [datas, setDatas] = useState([]);
    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`

            }
        };
        fetch(`/api/profil/user`, options)
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setDatas(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setApiError(error);
                }
            )
    }, [])

    let balise;
    if (apiError) balise = <QuestionProposeeError {...apiError}/>
    else if (!isLoaded) balise = <QuestionProposeeLoading/>
    else balise = <QuestionProposeeValide {...datas}/>

    return (
        <>
        <div className="d-flex justify-content-left p-3">
            <h1>Questions proposées</h1>
        </div>
        <Container fluid>
            {balise}
        </Container>
        </>
    );
}
function QuestionProposeeError(apiError){
    return (
        <Alert variant="danger">
            <Alert.Heading >Error</Alert.Heading>
            {apiError.message}
        </Alert>
    );
}

function QuestionProposeeLoading(){
    return(
        <Spinner variant="primary"/>
    );
}
function QuestionProposeeValide(data){
    return <p>CA MARCHE !</p>
}