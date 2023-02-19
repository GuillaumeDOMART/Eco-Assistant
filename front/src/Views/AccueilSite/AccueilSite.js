import {Col, Container, Row} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {Button, TextField} from "@mui/material";
// import leaves from './leaves.png';
import "./AccueilSite.css"
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

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
                <TextField label="Prénom" type="text" variant="standard" className="textfield m-2" {...register("firstname")} required/><br/>
                <TextField label="Nom" type="text" variant="standard" className="textfield m-2" {...register("lastname")} required/><br/>
                <TextField label="Adresse Mail" type="email" variant="standard" className="textfield m-2" {...register("mail")} required/><br/>
                <TextField label="Mot de passe" type="password" variant="standard" className="textfield m-2" {...register("password")} required/><br/>
                <TextField label="Valider le mot de passe" type="password" variant="standard" className="textfield m-2" {...register("passwordConfirmed")} required/><br/>
                <Button type="submit" className="text-black mb-4">Créer</Button><br/>
                <p>Déjà un compte ? <a href="/connexion">Se connecter</a></p>
            </form>
        </Col>
    )
}

/**
 * Display the left part of the home page of the website
 * @returns {JSX.Element}
 * @constructor
 */
const Anonyme = () => {
    return (
        <Col className="mx-5 my-5 shadow-lg p-3 mb-5 rounded-3 bg-white bg-opacity-75">
                <h1 className="m-3 mb-4 mt-3">Eco-Assistant</h1>
                {/*<img className="leaves" src={require('./leaves.png')}  alt={"leaves"}/>*/}
                <hr/>
                <p>Envie de connaitre<br/>
                    l&lsquo;empreinte carbone de<br/>
                    ton projet informatique ?<br/>
                    <br/>
                    Grâce au questionnaire<br/>
                    Eco-Assistant, calcule ton<br/>
                    impact environnemental :</p>
                <a href="/guest">Remplir le questionnaire*</a>
                <hr/>
                <p>* Remplir un questionnaire sans être connecté entrainera une perte<br/>
                    des données en cas d&lsquo;abandon. Pour conserver l&lsquo;avancement<br/>
                    connectez-vous ou créez un compte</p>
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

    /**
     * Send datas to the back
     * @param datas
     */
    const submitCreation = async (datas) => {

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        if(datas.password !== datas.passwordConfirmed){
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
               <Row className="vh-100">
                   <Connexion onSubmit={handleSubmit(submitCreation)} register={register}/>
                   <Anonyme/>
               </Row>
           </Container>
    )
}

export default AccueilSite