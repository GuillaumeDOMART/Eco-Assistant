import {Col, Container, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
import "./AccueilSite.css"
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";



/**
 * Display the left part of the home page of the website
 * @returns {JSX.Element}
 * @constructor
 */
const Anonyme = () => {
    return (
        <Col className="mx-5 my-5 shadow-lg p-3 mb-5 rounded-3 bg-white bg-opacity-75">
            <h1 className="Title"><img className="logo" src={require('../../Components/logo/Eco-Assistant_transparent.PNG')}  alt={"logo"}/>
                Eco-Assistant</h1>
            <hr className="opacity-100"/>
            <p className="Text">Envie de connaitre l&lsquo;empreinte<br/>
                carbone de ton projet<br/>
                informatique ?<br/>
                <br/>
                Grâce au questionnaire Eco-Assistant,<br/>
                calcule l&lsquo;impact environnemental<br/>
                de ton projet :</p>
            <a href="/guest" className="fs-5">Remplir le questionnaire</a>
            <hr className="opacity-100"/>
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
    const [paragraphContent, setParagraphContent] = useState("")

    /**
     * Display the right part of the home page of the website
     * @param handleSubmit
     * @param register
     * @returns {JSX.Element}
     * @constructor
     */
    const Connexion = ({onSubmit, register}) => {
        return (
            <Col className="mx-5 my-5 shadow-lg p-3 mb-5 rounded-3 bg-white bg-opacity-75 col-4">
                <h2 className="m-3">Créer un compte</h2>
                <form onSubmit={onSubmit}>
                    <TextField label="Prénom" type="text" variant="standard" className="textfield" {...register("firstname")} required/><br/>
                    <TextField label="Nom" type="text" variant="standard" className="textfield " {...register("lastname")} required/><br/>
                    <TextField label="Adresse Mail" type="email" variant="standard" className="textfield " {...register("mail")} required/><br/>
                    <TextField label="Mot de passe" type="password" variant="standard" className="textfield " {...register("password")} required/><br/>
                    <TextField label="Valider le mot de passe" type="password" variant="standard" className="textfield " {...register("passwordConfirmed")} required/><br/>
                    <p className="text-danger">{paragraphContent}</p>
                    <Button type="submit" className="text-black">Créer</Button><br/>
                    <p>Déjà un compte ? <a href="/connexion">Se connecter</a></p>
                </form>
                <p className="NB">Remplir un questionnaire sans être connecté entrainera une perte<br/>
                    des données en cas d&lsquo;abandon. Pour conserver l&lsquo;avancement<br/>
                    connectez-vous ou créez un compte</p>
            </Col>
        )
    }

    /**
     * Send datas to the back
     * @param datas
     */
    const submitCreation = async (datas) => {

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        if(datas.password !== datas.passwordConfirmed){
            setParagraphContent("Les mot de passe fournies ne corresponde pas")
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
        if(response.status === 403){
            setParagraphContent("Le mail est déjà utilisé pour un compte")
            return
        }
        const json = await response.json();
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
                   <Connexion onSubmit={handleSubmit(submitCreation)} register={register}/>
                   <Col className="col-1"></Col>
                   <Anonyme/>
               </Row>
           </Container>
    )
}

export default AccueilSite