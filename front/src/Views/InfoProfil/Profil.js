import BarreNavCore from "../../Components/BarreNav/BarreNavCore";
import React, {useCallback, useEffect, useState} from "react";
import {Button, Col, Container,Modal} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

/**
 * The component representing the information page for a profile
 * @returns {JSX.Element}
 * @constructor
 */
function Profil() {
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
    if (apiError) {
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
                <div className="col-10 p-5">
                    <InfoProfilContainer {...datas}/>
                </div>
            </div>);
    } else if (!isLoaded) {
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
            </div>
        );
    } else {
        return (
            <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
                <BarreNavCore/>
                <div className="col-10 p-5">
                    <InfoProfilContainer {...datas}/>
                </div>
            </div>
        );
    }
}




/**
 * Component that uses data fetched of profile
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
function InfoProfil(datas) {
    const prenom = `Prénom : ${datas.prenom}`
    const nom = `Nom : ${datas.nom}`
    const email = `Mail : ${datas.mail}`
    const navigate = useNavigate()
    const [show, setShow] = useState(false);

    /**
     * Hide pop-up if deletion of profile is refused
     */
    const handleCancel = useCallback(() => {
        setShow(false);
    },[setShow])
    /**
     * Show the pop-up when you push the button delete profil
     */
    const handleShow = useCallback(() => {
        setShow(true);
    },[setShow])

    const handleID = useCallback(() => {
        navigate("/modifyID");
    }, [navigate])

    const handlePassword = useCallback(() => {
        navigate("/modifyPassword");
    }, [navigate])
    return (
        <>
            <div className="d-flex justify-content-left p-3">
                <p>{prenom}</p>
            </div>
            <div className="d-flex justify-content-left p-3">
                <p>{nom}</p>
                <Col/>
            </div>
            <div className="d-flex justify-content-between p-3">
                <p>{email}</p>
                <Button onClick={handleID} variant='outline-warning'>Modifier l&lsquo;adresse email</Button>
            </div>
            <div className="d-flex justify-content-between p-3">
                <p>Mot de passe : ************</p>
                <Button variant="outline-warning" onClick={handlePassword}>Modifier le mot de passe</Button>
            </div>

            <div className="d-flex justify-content-center p-3">
                <Button variant="outline-danger" onClick={handleShow}>Supprimer le Profil</Button>
            </div>
            <Modal show={show} onHide={handleCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Supprimer le Profil</Modal.Title>
                </Modal.Header>
                <Modal.Body>Êtes-vous sûr de vouloir supprimer votre compte?</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCancel}>
                        Annuler
                    </Button>
                    <Button variant="outline-danger">
                        Supprimer
                    </Button>
                </Modal.Footer>
            </Modal>
        </>

    );
}


/**
 * Component that contain raw datas or data fetched if the token is not present
 * @param datas
 * @returns {JSX.Element}
 * @constructor
 */
function InfoProfilContainer(datas) {
    return (
        <>
            <div className="d-flex justify-content-left p-3">
                <h1>Profil</h1>
            </div>
            <Container fluid>
                <InfoProfil {...datas}/>
            </Container>
        </>
    );
}

export default Profil;