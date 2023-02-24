import {Col, Container, Modal, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import "./AccueilSite.css"
import {useNavigate} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";

/**
 * Display the right part of the home page of the website
 * @param handleSubmit
 * @param register
 * @returns {JSX.Element}
 * @constructor
 */
const Connexion = ({onSubmit, register, navigate, paragraphContent}) => {
    /**
     * Redirect to the connection page
     */
    const handleConnect = useCallback(() => {
        navigate("/connexion")
    }, [navigate])

    return (
        <Col className="mx-5 my-5 shadow-lg p-3 mb-5 rounded-3 bg-white bg-opacity-75 col-4">
            <h2 className="m-3">Créer un compte</h2>
            <form onSubmit={onSubmit}>
                <TextField label="Prénom" type="text" variant="standard" className="textfield" {...register("firstname")} required/><br/>
                <TextField label="Nom" type="text" variant="standard" className="textfield " {...register("lastname")} required/><br/>
                <TextField label="Adresse Mail" type="email" variant="standard" className="textfield " {...register("mail")} required/><br/>
                <TextField label="Mot de passe" type="password" variant="standard" className="textfield " {...register("password")} required/><br/>
                <TextField label="Valider le mot de passe" type="password" variant="standard" className="textfield " {...register("passwordConfirmed")} required/><br/>
                <p className="text-danger w-100 h-auto">{paragraphContent}</p>
                <Button type="submit" className="text-black mt-2" variant={"outline-primary"}>Créer</Button><br/>
                <p>Déjà un compte ? <Button onClick={handleConnect} className="text-black text-decoration-underline">Se
                    connecter</Button></p>
            </form>
            <p className="NB">Remplir un questionnaire sans être connecté entrainera une perte<br/>
                des données en cas d&lsquo;abandon. Pour conserver l&lsquo;avancement<br/>
                connecte-toi ou créez un compte</p>
        </Col>
    )
}

/**
 * Display the left part of the home page of the website
 * @returns {JSX.Element}
 * @constructor
 */
const Anonyme = ({navigate}) => {
    const [show, setShow] = useState(false);
    /**
     * Hide pop-up if deletion of profile is refused
     */
    const handleCancel = useCallback(() => {
        setShow(false);
    }, [setShow])
    /**
     * Show the pop-up when you push the button delete profil
     */
    const handleShow = useCallback(() => {
        setShow(true);
    }, [setShow])

    const handleAccept = useCallback(() => {
        setShow(false)
        navigate("/guest")
    }, [setShow, navigate])

    return (
        <Col className="mx-5 my-5 shadow-lg p-3 mb-5 rounded-3 bg-white bg-opacity-75">
            <h1 className="Title"><img className="logo"
                                       src={require('../../Components/logo/Eco-Assistant_transparent.PNG')}
                                       alt={"logo"}/>
                Eco-Assistant</h1>
            <hr/>
            <p className="Text">Envie de connaitre l&lsquo;empreinte<br/>
                carbone de ton projet<br/>
                informatique ?<br/>
                <br/>
                Grâce au questionnaire Eco-Assistant,<br/>
                calcule l&lsquo;impact environnemental<br/>
                de ton projet :</p>
            <Button onClick={handleShow} className="fs-5 text-black text-decoration-underline">Remplir le
                questionnaire</Button>
            <hr className="opacity-100"/>
            <Modal show={show} onHide={handleCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Attention !</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Tu es sur le point de remplir le questionnaire sans être connecté.
                    Si tu quittes, toutes les données remplies seront perdues.<br/>
                    Il te sera possible d&lsquo;accéder au résultat du questionnaire et de l&lsquo;exporter mais tu perdras l&lsquo;accès
                    une fois la page quittée.<br/>
                    Souhaites-tu continuer de manière anonyme ?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="outline-primary" className="text-black" onClick={handleAccept}>
                        Remplir le questionnaire
                    </Button>
                </Modal.Footer>
            </Modal>
        </Col>
    )
}

/**
 * Display the home page of the website
 * @returns {JSX.Element}
 * @constructor
 */
function AccueilSite() {
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();
    const [paragraphContent, setParagraphContent] = useState()

    /**
     * Send datas to the back
     * @param datas
     */
    const submitCreation = async (datas) => {

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        if (datas.password !== datas.passwordConfirmed) {
            setParagraphContent("Les mot de passe fournies ne correspondent pas")
            return
        }
        const jsonBody = {mail: datas.mail, password: datas.password, nom: datas.firstname, prenom: datas.lastname}
        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(jsonBody),
            redirect: 'follow'
        };

        const response = await fetch("api/auth/register", requestOptions);

        if (response.status === 403) {
            setParagraphContent("Le mail est déjà utilisé pour un compte")
            return
        }
        const json = await response.json();
        if (response.status === 400) {
            setParagraphContent("Le mot de passe n'est pas conforme (1 minuscule, 1 majuscule, 1 chiffre, 1 caractère spécial, longueur de 8 caractères minimum)");
            return;
        }

        sessionStorage.setItem("token", json.token);
        navigate("/profil")
    }

    useEffect(() => {
        const value = sessionStorage.getItem('token');
        if (value) {
            navigate("/profil")
        }
    }, [navigate]);


    return (
           <Container className="bg" fluid>
               <Row className="vh-100 align-items-center">
                   <Connexion onSubmit={handleSubmit(submitCreation)} register={register} navigate={navigate} paragraphContent={paragraphContent}/>
                   <Col className="col-1"></Col>
                   <Anonyme navigate={navigate}/>
               </Row>
           </Container>
    )
}

export default AccueilSite