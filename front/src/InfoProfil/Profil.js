import BarreNavCore from "../BarreNav/BarreNavCore";
import React, {useEffect, useState} from "react";
import {Button, Col, Container, Row} from "react-bootstrap";
import {TextField} from "@mui/material";
import {useNavigate} from "react-router-dom";


function Profil(){
    const [isLoaded, setIsLoaded] = useState(false);
    const [apiError, setApiError] = useState(null);
    const [datas, setDatas] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        const id = new URLSearchParams(window.location.search).get('id');
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id })
        };
        fetch("/api/profil",options)
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
    if(apiError){
        alert("API not fetched")
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
            </div>);
    } else if(!isLoaded){
        alert("Failed to load datas")
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
            </div>);
    }else{
        return(
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
                <div className="col-10 p-5">
                    <ActionBlockProfil datas={datas} navigate={navigate}/>
                </div>
            </div>
        );
    }

}


function ExampleInfoProfil(){
    const items ={
        "id": 1,
        "mail": "admin@demo.fr",
        "nom": "DEMO",
        "prenom": "Admin",
        "admin": true
    }

}
function ActionBlockProfil(datas,navigate){
    return(
        <Col>
            <InfoProfilContainer datas={datas} navigate={navigate}/>
            <DeleteProfilContainer/>
        </Col>

    );
}
function handleDeleteProfil(){
    //TODO POP UP Es tu sur de vouloir supprimer ton profil?

}
function InfoProfil(datas,navigate){
    return(
        <>
            <TextField> {datas.prenom}</TextField>
            <TextField> {datas.nom}</TextField>
            <Row>
                <TextField> {datas.mail}</TextField>
                <Button onClick={handleNavigate('/mail',navigate)} value={"Modifier l'identifiant"}/>
            </Row>
            <TextField>d</TextField>

        </>

    );
}
function handleNavigate(path,navigate){
    navigate(path)
}
function InfoProfilContainer (datas, navigate){
    return(
        <Container>
            <Col>
                <InfoProfil datas={datas} navigate={navigate}/>
            </Col>
        </Container>

    );
}
function DeleteProfilContainer(){
    return(
        <Container>
            <Row>
                <Button onClick={handleDeleteProfil} type={"button"} value="Supprimer le Profil"/>
            </Row>
        </Container>

    );
}
export default Profil;