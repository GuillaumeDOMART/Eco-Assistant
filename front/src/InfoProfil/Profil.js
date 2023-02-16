import BarreNavCore from "../BarreNav/BarreNavCore";
import React, {useEffect, useState} from "react";
import {Button, Col, Container, Row} from "react-bootstrap";
import {useNavigate} from "react-router-dom";


function Profil(){
    const [isLoaded, setIsLoaded] = useState(false);
    const [apiError, setApiError] = useState(null);
    const [datas, setDatas] = useState([]);
    useEffect(() => {
        const id = new URLSearchParams(window.location.search).get('id');
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`

            },
            body: JSON.stringify({ id })
        };
        fetch('/api/profil',options)
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
        console.log("API not fetched")
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
                <ActionBlockProfil datas={datas}/>
            </div>);
    } else if(!isLoaded){
        console.log("Failed to load datas")
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
            </div>
        );
    }else{
        return(
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
                <div className="col-10 p-5">
                    <ActionBlockProfil datas={datas}/>
                </div>
            </div>
        );
    }

}


function ExampleInfoProfil({navigate}){
    const items ={
        "id": 1,
        "mail": "admin@demo.fr",
        "nom": "DEMO",
        "prenom": "Admin",
        "admin": true
    }
    console.log("Print Example datas")

    return(
        <>
            <Row className="fs-1"><p>Profil</p></Row>
            <Row> {"Prénom : "+items.prenom}</Row>
            <Row> {"Nom : "+items.nom}</Row>
            <Row>
                <Row> {"Identifiant : "+items.mail}</Row>
                <Button onClick={()=>navigate("/mail")}>Modifier l'identifiant</Button>
            </Row>
            <Button onClick={()=>navigate("/")}>Modifier le mot de passe</Button>

        </>

    );

}
function ActionBlockProfil(datas){
    return(
        <Col>
            <InfoProfilContainer datas={datas}/>
            <DeleteProfilContainer/>
        </Col>

    );
}
function handleDeleteProfil(){
    //TODO POP UP Es tu sur de vouloir supprimer ton profil?
    const id = new URLSearchParams(window.location.search).get('id');



}
function InfoProfil(datas){
    const navigate = useNavigate();
    console.log("Print Fetched datas")
    return(
        <>
            <Row className="fs-1"><p>Profil</p></Row>
            <Row> {"Prénom : "+datas.prenom}</Row>
            <Row> {"Nom : "+datas.nom}</Row>
            <Row>
                <Row> {"Identifiant : "+datas.mail}</Row>
                <Button onClick={()=>navigate("/mail")} type={"button"}>Modifier l'identifiant</Button>
            </Row>
            <Button onClick={()=>navigate("/")}>Modifier le mot de passe</Button>

        </>

    );
}
/*function handleNavigate(path,navigate){
    navigate(path)
}*/
function InfoProfilContainer (datas){
    const token = sessionStorage.getItem("token");
    console.log(token);
    if(token == null){
        return(
            <Container>
                <Col>
                    {/*<InfoProfil datas={datas} navigate={navigate}/>*/}
                    <ExampleInfoProfil/>
                </Col>
            </Container>

        );
    }
    else{
        return(
            <Container>
                <Col>
                    <InfoProfil datas={datas}/>
                </Col>
            </Container>

        );
    }

}
function DeleteProfilContainer(){
    return(
        <Container>
            <Row>
                <Button onClick={handleDeleteProfil} type={"button"}>Supprimer le Profil</Button>
            </Row>
        </Container>

    );
}
export default Profil;