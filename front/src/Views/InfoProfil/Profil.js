import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useEffect, useState} from "react";
import {Button, Col, Container, Row} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

/**
 * The component representing the information page for a profile
 * @returns {JSX.Element}
 * @constructor
 */
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
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
                <ActionBlockProfil datas={datas}/>
            </div>);
    } else if(!isLoaded){
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



/**
 * Component of the right side of the profile informations screen
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
function ActionBlockProfil(datas){
    return(
        <Col>
            <InfoProfilContainer datas={datas}/>
            <DeleteProfilContainer/>
        </Col>

    );
}


/**
 * Component that uses data fetched of profile
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
function InfoProfil(datas){
    const navigate = useNavigate();

    const handleID = () => {
        navigate("/modifyID");
    }
    const handlePassword = () => {
        navigate("/modifyPassword");
    }
    return(
        <>
            <Row><p>Profil</p></Row>
            <Row> Prénom : ${datas.prenom}</Row>
            <Row> Nom : ${datas.nom}</Row>
            <Row>
                <Row> Identifiant : ${datas.mail}</Row>
                <Button onClick={handleID} type={"button"}>Modifier l&lsquo;identifiant</Button>
            </Row>
            <Button onClick={handlePassword}>Modifier le mot de passe</Button>

        </>

    );
}


/**
 * Component that contain raw datas or data fetched if the token is not present
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
function InfoProfilContainer (datas){
        return(
            <Container>
                <Col>
                    <InfoProfil datas={datas}/>
                </Col>
            </Container>

        );


}

/**
 * Component that holds the button for deleting the profile
 */
function DeleteProfilContainer(){
    const navigate = useNavigate()
    const handleDelete = () => {
        const id = new URLSearchParams(window.location.search).get('id');
        navigate("/")
    }

    return(
        <Container>
            <Row>
                <Button onClick={handleDelete} type={"button"}>Supprimer le Profil</Button>
            </Row>
        </Container>

    );
}
export default Profil;